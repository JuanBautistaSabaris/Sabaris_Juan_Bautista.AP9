package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public List<TransactionDTO> getTransactions() {
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());
    }
    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
    @Override
    public Transaction findById(long id) {
        return transactionRepository.findById(id).orElse(null);
    }
    @Override
    public TransactionDTO getTransaction(long id) {
        return transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
    }
    @Override
    public Transaction createDebitTransaction(double amount, String description) {
        return new Transaction(TransactionType.DEBIT,-amount,description, LocalDateTime.now());
    }

    @Override
    public Transaction createCreditTransaction(double amount, String description) {
        return new Transaction(TransactionType.CREDIT,amount,description, LocalDateTime.now());
    }
}
