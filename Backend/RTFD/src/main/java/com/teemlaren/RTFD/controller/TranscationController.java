package com.teemlaren.RTFD.controller;

import com.teemlaren.RTFD.entity.Transaction;
import com.teemlaren.RTFD.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TranscationController {
    @Autowired
    private final TransactionRepository repo;
     public TranscationController(TransactionRepository repo){
         this.repo=repo;
     }
    @GetMapping
    public List<Transaction> all(){
        return repo.findAll();

    }
    @PostMapping
    public Transaction Create(@RequestBody Transaction tx){
         return repo.save(tx);
    }
}
