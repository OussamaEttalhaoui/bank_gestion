package com.example.accountservice.dto;


import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
}
