package com.example.accountservice.controller;

import com.example.accountservice.entity.Compte;
import com.example.accountservice.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    @Autowired
    private CompteService compteService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @PostMapping("/create/{customerId}")
    public ResponseEntity<Compte> createAccount(@PathVariable Long customerId, @RequestBody Compte compte) {
        try {
            Compte createdCompte = compteService.createAccount(customerId, compte);
            return ResponseEntity.ok(createdCompte);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @GetMapping("/proprietaire/{id}")
    public ResponseEntity<List<Compte>> getComptesByProprietaire(@PathVariable Long id) {
        return ResponseEntity.ok(compteService.getComptesByProprietaire(id));
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping("/numero/{numero}")
    public ResponseEntity<Optional<Compte>> getCompteByNumero(@PathVariable String numero) {
        return ResponseEntity.ok(compteService.getCompteByNumero(numero));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCompte(@PathVariable Long id) {
        compteService.supprimerCompte(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @GetMapping("/")
    public ResponseEntity<List<Compte>> getAllAccounts() {
        List<Compte> comptes = compteService.getAllAccounts();
        return ResponseEntity.ok(comptes);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        Compte compte = compteService.findById(id);
        if (compte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(compte);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @PutMapping("/{id}")
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id, @RequestBody Compte compte) {
        Compte updatedCompte = compteService.updateCompte(id, compte);
        if (updatedCompte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCompte);
    }
}
