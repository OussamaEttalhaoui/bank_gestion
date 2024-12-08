package com.example.transactionservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping("/create/{compteSourceId}/{compteDestinationId}")
    public ResponseEntity<Transaction> createTransaction(
            @PathVariable Long compteSourceId,
            @PathVariable Long compteDestinationId,
            @RequestParam Double montant,
            @RequestParam String typeTransaction) {
        Transaction transaction = transactionService.createTransaction(compteSourceId, compteDestinationId, montant, typeTransaction);
        return ResponseEntity.ok(transaction);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping("/list/{compteSourceId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCompteSourceId(@PathVariable Long compteSourceId) {
        List<Transaction> transactions = transactionService.getTransactionsByCompteSourceId(compteSourceId);
        return ResponseEntity.ok(transactions);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
