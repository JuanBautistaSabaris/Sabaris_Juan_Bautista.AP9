package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args) -> {
		Client client = new Client("Melba", "Morel" , "melba@mindhub.com", passwordEncoder.encode("elbajito") );

			Client client1 = new Client("Bautista", "Sabaris" , "bautisab@mindhub.com", passwordEncoder.encode("crack") );

			Client client2 = new Client("Admin", "Admin", "admin@mindhub.com", passwordEncoder.encode("soyadmin"));

			Account account = new Account("VIN001", LocalDateTime.now(),7500);
			client.addAccount(account);

			Transaction transaction = new Transaction(TransactionType.DEBIT,-2300,"Taxes",LocalDateTime.now());
			account.addTransaction(transaction);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,6500,"Loan",LocalDateTime.now());
			account.addTransaction(transaction1);

			Account account1 = new Account("VIN002", LocalDateTime.now().plusDays(1),9000);
			client.addAccount(account1);

			Transaction transaction2 = new Transaction(TransactionType.CREDIT,4000,"Loan",LocalDateTime.now().plusDays(1));
			account1.addTransaction(transaction2);

			Transaction transaction3 = new Transaction(TransactionType.DEBIT,-3000,"Debt",LocalDateTime.now().plusDays(1));
			account1.addTransaction(transaction3);

			Account account2 = new Account("VIN012", LocalDateTime.now(),12000);
			client1.addAccount(account2);

			Transaction transaction4 = new Transaction(TransactionType.DEBIT,-4200,"Taxes",LocalDateTime.now());
			account2.addTransaction(transaction4);

			Transaction transaction5 = new Transaction(TransactionType.CREDIT,8000,"Loan",LocalDateTime.now());
			account2.addTransaction(transaction5);

			List<Integer> payments =Arrays.asList (12,24,36,48,60);
			List<Integer> payments2 = Arrays.asList(6,12,24);
			List<Integer> payments3 = Arrays.asList(6,12,24,36);

			Loan loan1= new Loan("Hipotecario",500000.0,payments);

			Loan loan2= new Loan("Personal", 100000.0,payments2);

			Loan loan3= new Loan("Automotriz", 300000.0,payments3);

			ClientLoan clientLoan1= new ClientLoan(400000.0,60);
			client.addClientLoan(clientLoan1);
			loan1.addClientLoan(clientLoan1);

			ClientLoan clientLoan2= new ClientLoan(50000.0,12);
			client.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan2);

			ClientLoan clientLoan3= new ClientLoan(100000.0,24);
			client1.addClientLoan(clientLoan3);
			loan2.addClientLoan(clientLoan3);

			ClientLoan clientLoan4= new ClientLoan(200000.0,36);
			client1.addClientLoan(clientLoan4);
			loan3.addClientLoan(clientLoan4);

			Card card = new Card(client.toString(),CardType.DEBIT, CardColor.GOLD, "0532 2786 1904 9427",183,LocalDateTime.now(),LocalDateTime.now().plusYears(5));
			client.addCard(card);

			Card card1 = new Card(client.toString(),CardType.CREDIT, CardColor.TITANIUM, "0825 2751 4730 1865",625,LocalDateTime.now(),LocalDateTime.now().plusYears(5));
			client.addCard(card1);

			Card card2 = new Card(client1.toString(),CardType.DEBIT, CardColor.SILVER, "2093 5489 2913 5903",220,LocalDateTime.now(),LocalDateTime.now().plusYears(5));
			client1.addCard(card2);

			clientRepository.save(client);
			clientRepository.save(client1);
			clientRepository.save(client2);
			accountRepository.save(account);
			accountRepository.save(account1);
			accountRepository.save(account2);
			transactionRepository.save(transaction);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
			cardRepository.save(card);
			cardRepository.save(card1);
			cardRepository.save(card2);
		};
	}

}
