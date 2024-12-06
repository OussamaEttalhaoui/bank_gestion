package com.example.transactionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "account-service", url = "http://localhost:8082")
public interface AccountClient {
    @GetMapping("/api/comptes/{id}")
    CompteDTO getCompteById(@PathVariable Long id);

    @PutMapping("/api/comptes/{id}")
    CompteDTO updateCompte(@PathVariable Long id, @RequestBody CompteDTO compteDTO);
}

