package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args) -> {
		Client client = new Client("Melba", "Morel" , "melba@mindhub.com" );
				clientRepository.save(client);

			Account account = new Account("VIN001", LocalDate.now(),7500);
			client.addAccount(account);
			accountRepository.save(account);

			Transaction transaction = new Transaction(TransactionType.DEBIT,-2300,"Taxes",LocalDate.now());
			account.addTransaction(transaction);
			transactionRepository.save(transaction);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,6500,"Loan",LocalDate.now());
			account.addTransaction(transaction1);
			transactionRepository.save(transaction1);

			Account account1 = new Account("VIN002", LocalDate.now().plusDays(1),9000);
			client.addAccount(account1);
			accountRepository.save(account1);

			Transaction transaction2 = new Transaction(TransactionType.CREDIT,4000,"Loan",LocalDate.now().plusDays(1));
			account1.addTransaction(transaction2);
			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction(TransactionType.DEBIT,-3000,"Debt",LocalDate.now().plusDays(1));
			account1.addTransaction(transaction3);
			transactionRepository.save(transaction3);
		};
	}

}
