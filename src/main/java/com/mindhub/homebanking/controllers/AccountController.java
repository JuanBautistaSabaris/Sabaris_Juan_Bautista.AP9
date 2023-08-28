package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id){
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    public String accountNumberGenerator() {
        String prefix = "VIN-";
        int maxNum = 99999999;
        int accountNumber = (int) (Math.random() * maxNum) + 1;
        String accountNumberStr = String.format("%06d", accountNumber);
        return prefix + accountNumberStr;
    }

    public void accCreator(Client currentClient){
        LocalDate today = LocalDate.now();
        Account currentAccount = new Account(accountNumberGenerator(), today, 0);
        currentClient.addAccount(currentAccount);
        accountRepository.save(currentAccount);
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client currentClient = clientRepository.findByEmail(authentication.getName());
        if (currentClient.getAccounts().size() >= 3) {
            return new ResponseEntity<>("E403 FORBIDDEN", HttpStatus.FORBIDDEN);
        } else {
            accCreator(currentClient);
            return new ResponseEntity<>("201 CREATED", HttpStatus.CREATED);
        }
    }
}
