package com.vaha1st.sberCredit.dao;

import com.vaha1st.sberCredit.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
