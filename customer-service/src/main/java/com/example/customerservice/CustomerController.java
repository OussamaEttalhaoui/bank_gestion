package com.example.customerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        // Convertir l'entité Customer en DTO
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setNom(customer.getNom());
        customerDTO.setNumeroTelephone(customer.getNumeroTelephone());
        customerDTO.setEmail(customer.getEmail());

        return ResponseEntity.ok(customerDTO);
    }

    @PostMapping("/creer")
    public ResponseEntity<Customer> creerCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.creerCustomer(customer));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
}
