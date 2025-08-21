package com.teemlaren.RTFD.repository;

import com.teemlaren.RTFD.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long>{
    Optional<Transaction> findBymerchantId(String merchantId);

}
