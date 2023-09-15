package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class LoanController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientLoanService clientLoanService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoans();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoans(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        //Validate CLIENT
        boolean hasClientAuthority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("CLIENT"));
        if (!hasClientAuthority) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        //Get client
        Client client = clientService.findByEmail(authentication.getName());
        //Loan exists
        Loan loan = loanService.findById(loanApplicationDTO.getLoanId());
        //Account belongs to client
        Account destinationAccount = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());

        //Validate params
        if (loanApplicationDTO.getLoanId() == 0) {
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() <= 0) {
            return new ResponseEntity<>("Choose an amount", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getPayments() <= 0) {
            return new ResponseEntity<>("Choose a payment", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getToAccountNumber().isEmpty()) {
            return new ResponseEntity<>("Put an account number", HttpStatus.FORBIDDEN);
        }
        //Loan exists
        if (loan == null) {
            return new ResponseEntity<>("Loan no exist", HttpStatus.FORBIDDEN);
        }
        //Verify amount requested
        if (loan.getMaxAmount() < loanApplicationDTO.getAmount()) {
            return new ResponseEntity<>("Loan no exist", HttpStatus.FORBIDDEN);
        }
        //Verify amount of payments
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("Payments incorrect", HttpStatus.FORBIDDEN);
        }
        //Account exists
        if (destinationAccount == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.FORBIDDEN);
        }
        //Client exists
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        //Verify account belongs to the client
        if (!client.getAccounts().contains(destinationAccount)) {
            return new ResponseEntity<>("The account does not belong to the current client", HttpStatus.FORBIDDEN);
        }

        //New clientLoan
        ClientLoan newLoan = clientLoanService.createClientLoan(loanApplicationDTO.getAmount() * 1.2,loanApplicationDTO.getPayments());
        newLoan.setClient(client);
        newLoan.setLoan(loan);
        client.addClientLoan(newLoan);
        loan.addClientLoan(newLoan);
        //Create transaction
        Transaction creditTransaction = transactionService.createCreditTransaction(loanApplicationDTO.getAmount(), loan.getName() + " loan approved");
        //Add transaction to client
        destinationAccount.addTransaction(creditTransaction);
        //Add amount to account
        destinationAccount.plusBalance(loanApplicationDTO.getAmount());
        //Save account
        accountService.saveAccount(destinationAccount);
        transactionService.saveTransaction(creditTransaction);
        clientLoanService.saveClientLoan(newLoan);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
