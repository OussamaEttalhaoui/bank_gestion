package com.example.accountservice.service;

import com.example.accountservice.dto.CustomerDTO;
import com.example.accountservice.entity.Compte;
import com.example.accountservice.feign.CustomerClient;
import com.example.accountservice.repository.CompteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;

    private final CustomerClient customerClient;


    public Compte createAccount(Long customerId, Compte compte) {
        CustomerDTO customer = customerClient.getCustomerById(customerId);
        if (customer == null) {
            throw new RuntimeException("Customer not found!");
        }

        compte.setProprietaireId(customer.getId());
        compte.setNumeroCompte("ACC-" + System.currentTimeMillis());

        return compteRepository.save(compte);
    }

    public Compte updateCompte(Long id, Compte compte) {
        Optional<Compte> existingCompte = compteRepository.findById(id);
        if (existingCompte.isPresent()) {
            Compte compteToUpdate = existingCompte.get();
            compteToUpdate.setSolde(compte.getSolde());
            compteToUpdate.setNumeroCompte(compte.getNumeroCompte());
            compteToUpdate.setTypeCompte(compte.getTypeCompte());
//            compteToUpdate.setDateCreation(compte.getDateCreation());
            return compteRepository.save(compteToUpdate);
        }
        return null;
    }

    public Compte findById(Long id) {
        return compteRepository.findById(id).orElse(null);
    }

    public List<Compte> getComptesByProprietaire(Long proprietaireId) {
        return compteRepository.findByProprietaireId(proprietaireId);
    }

    public Optional<Compte> getCompteByNumero(String numeroCompte) {
        return compteRepository.findByNumeroCompte(numeroCompte);
    }

    public void supprimerCompte(Long id) {
        compteRepository.deleteById(id);
    }
    public List<Compte> getAllAccounts() {
        return compteRepository.findAll();
    }
}
