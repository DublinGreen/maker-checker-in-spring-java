package com.longbridge.greendemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.longbridge.greendemo.model.Transaction;

/**
 * The interface Transaction repository.
 *
 * @author idisimagha dublin-green
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
