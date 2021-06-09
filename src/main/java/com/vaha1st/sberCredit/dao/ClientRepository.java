package com.vaha1st.sberCredit.dao;

import com.vaha1st.sberCredit.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    public List<Client> findAllByOrderByLastNameAsc();
}