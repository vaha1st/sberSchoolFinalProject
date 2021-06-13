package com.vaha1st.sberCredit.dao;

import com.vaha1st.sberCredit.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Integer> {
    public Role getByName(String name);
}
