package com.vaha1st.sberCredit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_scheme")
public class CreditScheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    String name;

    @Column(name = "min_sum")
    Integer minSum;

    @Column(name = "max_sum")
    Integer maxSum;

    Float rate;

    @Column(name = "max_period")
    Integer period;
}
