package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository){
		return (args) -> {
		Client client = new Client("Melba", "Morel" , "melba@mindhub.com" );

			Client client1 = new Client("Bautista", "Sabaris" , "bautisab@mindhub.com" );

			Account account = new Account("VIN001", LocalDate.now(),7500);
			client.addAccount(account);

			Transaction transaction = new Transaction(TransactionType.DEBIT,-2300,"Taxes",LocalDate.now());
			account.addTransaction(transaction);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,6500,"Loan",LocalDate.now());
			account.addTransaction(transaction1);

			Account account1 = new Account("VIN002", LocalDate.now().plusDays(1),9000);
			client.addAccount(account1);

			Transaction transaction2 = new Transaction(TransactionType.CREDIT,4000,"Loan",LocalDate.now().plusDays(1));
			account1.addTransaction(transaction2);

			Transaction transaction3 = new Transaction(TransactionType.DEBIT,-3000,"Debt",LocalDate.now().plusDays(1));
			account1.addTransaction(transaction3);

			Account account2 = new Account("VIN012", LocalDate.now(),12000);
			client1.addAccount(account2);

			Transaction transaction4 = new Transaction(TransactionType.DEBIT,-4200,"Taxes",LocalDate.now());
			account2.addTransaction(transaction4);

			Transaction transaction5 = new Transaction(TransactionType.CREDIT,8000,"Loan",LocalDate.now());
			account2.addTransaction(transaction5);

			List<Integer> payments =Arrays.asList (12,24,36,48,60);
			List<Integer> payments2 = Arrays.asList(6,12,24);
			List<Integer> payments3 = Arrays.asList(6,12,24,36);

			Loan loan1= new Loan("Hipotecario",500000.0,payments);

			Loan loan2= new Loan("Personal", 100000.0,payments2);

			Loan loan3= new Loan("Automotriz", 300000.0,payments3);

			ClientLoan clientLoan1= new ClientLoan(400000.0,60,client,loan1);

			ClientLoan clientLoan2= new ClientLoan(50000.0,12,client,loan2);

			ClientLoan clientLoan3= new ClientLoan(100000.0,24,client1,loan2);

			ClientLoan clientLoan4= new ClientLoan(200000.0,36,client1,loan3);

			clientRepository.save(client);
			clientRepository.save(client1);
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

		};
	}

}
