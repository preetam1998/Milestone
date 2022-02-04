package com.example.second.dao;

import com.example.second.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long>{


	Transaction findById(long id);
}
