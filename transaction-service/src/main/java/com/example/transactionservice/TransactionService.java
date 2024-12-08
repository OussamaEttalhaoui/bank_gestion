package com.example.transactionservice;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountClient accountClient;


    @Transactional
    public Transaction createTransaction(Long compteSourceId, Long compteDestinationId, Double montant, String typeTransactionStr) {

        CompteDTO compteSource = accountClient.getCompteById(compteSourceId);
        CompteDTO compteDestination = accountClient.getCompteById(compteDestinationId);

        if (compteSource == null || compteDestination == null) {
            throw new RuntimeException("Compte source ou destination non trouvé");
        }

        if (compteSource.getSolde() < montant) {
            throw new RuntimeException("Fonds insuffisants dans le compte source");
        }

        // Convertir le typeTransactionStr en TypeTransaction (Enum)
        TypeTransaction typeTransaction;
        try {
            typeTransaction = TypeTransaction.valueOf(typeTransactionStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Type de transaction invalide : " + typeTransactionStr);
        }

        compteSource.setSolde(compteSource.getSolde() - montant);
        compteDestination.setSolde(compteDestination.getSolde() + montant);

        accountClient.updateCompte(compteSourceId, compteSource);
        accountClient.updateCompte(compteDestinationId, compteDestination);

        Transaction transaction = new Transaction();
        transaction.setMontant(montant);
        transaction.setTypeTransaction(typeTransaction);
        transaction.setCompteSourceId(compteSourceId);
        transaction.setCompteDestinationId(compteDestinationId);
        transaction.setDateTransaction(new Date());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByCompteSourceId(Long compteSourceId) {
        return transactionRepository.findByCompteSourceId(compteSourceId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}
