package com.vaha1st.sberCredit.dao;

import com.vaha1st.sberCredit.entity.Client;
import com.vaha1st.sberCredit.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Integer> {
    public Optional<Credit> findByClientAndFinishedIsFalseOrFinishedIsNull(Client client);
}
