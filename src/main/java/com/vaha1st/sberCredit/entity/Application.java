package com.vaha1st.sberCredit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheme")
    private CreditScheme creditScheme;

    private Integer sum;

    private Integer period;

    private Float payment;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "pre_approved")
    private boolean preApproved;
}
