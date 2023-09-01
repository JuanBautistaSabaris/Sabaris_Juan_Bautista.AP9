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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getClient(@PathVariable Long id, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findById(id).orElse(null);
        if (client== null){
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        if( account == null){
            return new ResponseEntity<>("Account not found", HttpStatus.FORBIDDEN);
        }
        if (account.getOwnerAccount().getId().equals(client.getId())) {
            return new ResponseEntity<>(new AccountDTO(account), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("You're  not the owner", HttpStatus.FORBIDDEN);
    }

    public String accountNumberGenerator() {
        String prefix = "VIN-";
        int maxNum = 99999999;
        int accountNumber = (int) (Math.random() * maxNum) + 1;
        String accountNumberStr = String.format("%06d", accountNumber);
        return prefix + accountNumberStr;
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        boolean hasClientAuthority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("CLIENT"));
        if (!hasClientAuthority) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("User has 3 accounts", HttpStatus.FORBIDDEN);
        }
        String accountNumber = accountNumberGenerator();
        Account newAccount = new Account(accountNumber, LocalDateTime.now(), 0.0);
        client.addAccount(newAccount);
        accountRepository.save(newAccount);
        clientRepository.save(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getClientAccount(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getAccounts().stream().map(AccountDTO::new).collect(toList());
    }
}
