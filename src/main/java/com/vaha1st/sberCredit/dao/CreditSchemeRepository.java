package com.vaha1st.sberCredit.dao;

import com.vaha1st.sberCredit.entity.CreditScheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditSchemeRepository extends JpaRepository<CreditScheme, Integer> {
}
