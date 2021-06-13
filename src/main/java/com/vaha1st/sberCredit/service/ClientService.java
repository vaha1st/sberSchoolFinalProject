package com.vaha1st.sberCredit.service;

import com.vaha1st.sberCredit.entity.Client;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface ClientService extends UserDetailsService {

    public List<Client> getClients();
    public Client getSingleClient(int id);
    public void saveOrUpdateClient(Client client);
    public void deleteClient(int id);
    Client getSingleClientByUserName(String username);
}
