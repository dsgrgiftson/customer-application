package org.giftson.david.abnamro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMER_DETAILS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CustomerDetailsSequence")
    @SequenceGenerator(name = "CustomerDetailsSequence", sequenceName = "CUSTOMER_DETAILS_SEQUENCE", allocationSize = 1)
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;

    @Column(name = "IBAN_NUMBER", unique = true)
    private String ibanNumber;
}