package com.vaha1st.sberCredit.dao;

import com.vaha1st.sberCredit.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    public List<Client> findAllByOrderByLastNameAsc();
    public Optional<Client> findByUsername(String userName);
}