package com.example.library_management.repository;

import com.example.library_management.model.Book;
import com.example.library_management.model.Student;
import com.example.library_management.model.Transaction;
import com.example.library_management.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Transaction findTransactionByStudentAndBookAndTransactionTypeOrderByIdDesc(Student student, Book book, TransactionType transactionType);

}
