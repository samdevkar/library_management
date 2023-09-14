package com.example.library_management.repository;

import com.example.library_management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    @Query("Select s from Student s where s.name=:name")
    List<Student>findStudentByName(String name);
    @Query("Select s from Student s where s.email=:email")
    List<Student>findStudentByEmail(String email);
    @Query("Select s from Student s where s.rollNumber=:rollNumber")
    List<Student>findStudentByRollNumber(String rollNumber);

}
