package com.example.accountservice.feign;

import com.example.accountservice.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "http://localhost:8081") // Change l'URL si nécessaire
public interface CustomerClient {

    @GetMapping("/api/customers/{id}")
    CustomerDTO getCustomerById(@PathVariable Long id);
}
