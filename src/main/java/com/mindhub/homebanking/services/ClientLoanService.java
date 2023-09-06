package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.ClientLoan;

import java.util.List;

public interface ClientLoanService {
    List<ClientLoanDTO> getClientLoans();
    ClientLoan findById(long id);
    void saveClientLoan(ClientLoan clientLoan);
    ClientLoanDTO getClientLoan(long id);
    ClientLoan createClientLoan(double amount,int payments);
}
