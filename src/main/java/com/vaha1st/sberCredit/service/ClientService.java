package com.vaha1st.sberCredit.service;

import com.vaha1st.sberCredit.entity.Client;

import java.util.List;

public interface ClientService {

    public List<Client> getClients();
    public Client getSingleClient(int id);
    public void saveOrUpdateClient(Client client);
    public void deleteClient(int id);
}
