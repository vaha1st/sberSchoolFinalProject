package com.vaha1st.sberCredit.dao;

import com.vaha1st.sberCredit.entity.Client;
import com.vaha1st.sberCredit.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Integer> {
}