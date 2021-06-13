package com.vaha1st.sberCredit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String patronymic;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "passport_ser")
    private String passportSer;

    @Column(name = "passport_num")
    private String passportNum;

    private String address;

    @Column(name = "personal_data_accept")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean personalDataAccept;
}
