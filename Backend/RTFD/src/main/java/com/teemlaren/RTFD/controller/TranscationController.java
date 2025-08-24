package com.teemlaren.RTFD.controller;

import com.teemlaren.RTFD.entity.Transaction;
import com.teemlaren.RTFD.repository.TransactionRepository;
import com.teemlaren.RTFD.service.FraudDetectionService;
import com.teemlaren.RTFD.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TranscationController {
    @Autowired
    private final TransactionRepository repo;
    @Autowired
    private final TransactionService service;
    @Autowired
    private FraudDetectionService fraudDetectionService;

    public TranscationController(TransactionService service,TransactionRepository repo) {
        this.service=service;
        this.repo = repo;
    }

    @GetMapping
    public List<Transaction> all() {
        return repo.findAll();
    }
    @GetMapping("/{id}")

    public Optional<?> getByID(@PathVariable String id){
        return repo.findBymerchantId(id);
    }

    @PostMapping
    public void Create(@RequestBody Transaction tx) {
//        Transaction evaluatedTx = fraudDetectionService.evaluate(tx);
//        return repo.save(tx);
        Transaction evalTX = fraudDetectionService.evaluate(tx);
        //return repo.save(evalTX);

    }
}
