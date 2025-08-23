package com.teemlaren.RTFD.repository;

import com.teemlaren.RTFD.entity.FraudDecision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudDecisionRepository  extends JpaRepository<FraudDecision,Long> {
}
