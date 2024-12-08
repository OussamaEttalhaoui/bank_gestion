package com.example.customerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setNom(customer.getNom());
        customerDTO.setNumeroTelephone(customer.getNumeroTelephone());
        customerDTO.setEmail(customer.getEmail());

        return ResponseEntity.ok(customerDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/creer")
    public ResponseEntity<Customer> creerCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.creerCustomer(customer));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @PutMapping("/modifier/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            return ResponseEntity.notFound().build();
        }
        existingCustomer.setNom(updatedCustomer.getNom());
        existingCustomer.setNumeroTelephone(updatedCustomer.getNumeroTelephone());
        existingCustomer.setEmail(updatedCustomer.getEmail());

        Customer savedCustomer = customerService.updateCustomer(existingCustomer);
        return ResponseEntity.ok(savedCustomer);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            return ResponseEntity.notFound().build();
        }
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/list")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    @GetMapping ("/mySession")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }
}
