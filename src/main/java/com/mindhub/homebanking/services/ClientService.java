package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClients();
    void saveClient(Client client);
    Client findById(long id);
    ClientDTO getClient(long id);

    Client findByEmail(String email);

    ClientDTO getClientCurrent(String email);

}