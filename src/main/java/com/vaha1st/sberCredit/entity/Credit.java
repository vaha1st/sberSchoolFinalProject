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
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "form")
    private Form form;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app")
    private Application app;

    private String status;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean finished;
}
