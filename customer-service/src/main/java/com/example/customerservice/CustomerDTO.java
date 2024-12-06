package com.example.customerservice;


import lombok.Data;


@Data
public class CustomerDTO {
    private Long id;
    private String nom;
    private String email;
    private String numeroTelephone;
}