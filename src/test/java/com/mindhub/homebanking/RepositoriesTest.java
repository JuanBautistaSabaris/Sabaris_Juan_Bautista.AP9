package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

@DataJpaTest

@AutoConfigureTestDatabase(replace = NONE)

public class RepositoriesTest {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void existLoans() {
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, is(not(empty())));
    }

    @Test
    public void existPersonalLoan() {
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Test
    public void existClients() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    public void firstNameAndLastNameNotNull() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, allOf(hasItem(hasProperty("firstName", notNullValue())),
                hasItem(hasProperty("lastName", notNullValue()))));
    }

    @Test
    public void existAccounts() {
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void accountNumberNotNull() {
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, allOf(hasItem(hasProperty("number", notNullValue()))));
    }

    @Test
    public void existCards() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }

    @Test
    public void cardNumberNotNull() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, allOf(hasItem(hasProperty("number", notNullValue()))));
    }

    @Test
    public void existClientLoans() {
        List<ClientLoan> clientLoans = clientLoanRepository.findAll();
        assertThat(clientLoans, is(not(empty())));
    }

    @Test
    public void clientNotNull() {
        List<ClientLoan> clientLoans = clientLoanRepository.findAll();
        assertThat(clientLoans, allOf(hasItem(hasProperty("user", notNullValue()))));
    }

    @Test
    public void existTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    public void descriptionNotNull() {
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, allOf(hasItem(hasProperty("description", notNullValue()))));
    }

}

