package com.example.library_management.service;

import com.example.library_management.model.Student;
import com.example.library_management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    public void createStudent(Student student){
        studentRepository.save(student);
    }
    public List<Student> findStudent(String searchKey, String searchValue) throws Exception{
        switch (searchKey) {
            case "name" -> {
                return studentRepository.findStudentByName(searchValue);
            }
            case "email" -> {
                return studentRepository.findStudentByEmail(searchValue);
            }
            case "rollNumber" -> {
                return studentRepository.findStudentByRollNumber(searchValue);
            }
            case "id" -> {
                Optional<Student> student = studentRepository.findById(Integer.parseInt(searchValue));
                return student.map(Arrays::asList).orElseGet(ArrayList::new);
            }
            default -> throw new Exception("Search Key is not valid" + searchKey);
        }
    }
}
