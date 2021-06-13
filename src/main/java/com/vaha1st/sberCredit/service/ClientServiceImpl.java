package com.vaha1st.sberCredit.service;

import com.ibm.icu.text.Transliterator;
import com.vaha1st.sberCredit.dao.ClientRepository;
import com.vaha1st.sberCredit.dao.RolesRepository;
import com.vaha1st.sberCredit.entity.Client;
import com.vaha1st.sberCredit.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    private ClientRepository clientRepository;

    private RolesRepository rolesRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, RolesRepository rolesRepository) {
        this.clientRepository = clientRepository;
        this.rolesRepository = rolesRepository;
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
    public Client getSingleClientByUserName(String username) {
        Optional<Client> result = clientRepository.findByUsername(username);

        Client client = null;

        if (result.isPresent()) {
            client = result.get();
        } else {
            throw new RuntimeException("Клиента с логином =" + username + " не найдено");
        }
        return client;
    }

    @Override
    public void saveOrUpdateClient(Client client) {
        if (client.getUsername().isEmpty()) {
            var CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
            Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
            String transFirstName = toLatinTrans.transliterate(client.getFirstName()).toLowerCase(Locale.ROOT);
            String transLastName = toLatinTrans.transliterate(client.getLastName()).toLowerCase(Locale.ROOT);
            client.setUsername(transFirstName.charAt(0) + transLastName);
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setRoles(Collections.singletonList(rolesRepository.getByName("ROLE_CLIENT")));
        clientRepository.save(client);
    }

    @Override
    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Client> result = clientRepository.findByUsername(s);

        Client client = null;

        if (result.isPresent()) {
            client = result.get();
        } else {
            throw new RuntimeException("Неправильный логин или пароль =" + s + " не найдено");
        }

        return new org.springframework.security.core.userdetails.User(client.getUsername(), client.getPassword(),
                mapRolesToAuthorities(client.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
