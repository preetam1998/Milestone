package com.example.second.dao;

import com.example.second.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer>{


  @Query(value="SELECT * FROM transaction WHERE payee = ?1 OR payer = ?1 ORDER BY create_time DESC", nativeQuery = true)
  Iterable<Transaction> findByMobileNumber(String mobileNumber);
}
