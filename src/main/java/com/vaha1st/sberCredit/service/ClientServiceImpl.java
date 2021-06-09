package com.vaha1st.sberCredit.service;

import com.vaha1st.sberCredit.dao.ClientRepository;
import com.vaha1st.sberCredit.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService{

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public Client getSingleClient(int id) {
        Optional<Client> result = clientRepository.findById(id);

        Client client = null;

        if (result.isPresent()) {
            client = result.get();
        } else {
            throw new RuntimeException("Клиента под id=" + id + " не найдено");
        }
        return client;
    }

    @Override
    public void saveOrUpdateClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }
}
