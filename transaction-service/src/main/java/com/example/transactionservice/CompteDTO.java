package com.example.transactionservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteDTO {

    private Long id;
    private String numeroCompte;
    private Double solde;
    private String typeCompte;
}
