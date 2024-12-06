package com.example.transactionservice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;

    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;

    private Long compteSourceId;

    private Long compteDestinationId;


    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTransaction;

    public Date getDateTransaction() {
        return dateTransaction;
    }
    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

}