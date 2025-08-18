package com.teemlaren.RTFD.repository;

import com.teemlaren.RTFD.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{

}
