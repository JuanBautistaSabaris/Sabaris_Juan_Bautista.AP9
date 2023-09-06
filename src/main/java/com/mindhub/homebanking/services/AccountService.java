package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAccounts();
    void saveAccount(Account account);
    Account findById(long id);
    Account findByNumber(String number);
    AccountDTO getAccount(long id);
    Account createAccount();

    boolean existsByNumber(String accountNumber);
    String createNumberAccount();


}
