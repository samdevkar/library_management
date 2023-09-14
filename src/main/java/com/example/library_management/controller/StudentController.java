package com.example.library_management.controller;

import com.example.library_management.dto.CreateStudentRequest;
import com.example.library_management.dto.SearchRequest;
import com.example.library_management.model.Student;
import com.example.library_management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;
    @PostMapping("/student")
    public void createStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest){
        studentService.createStudent(createStudentRequest.toStudent());
    }
    @GetMapping("/getstudents")
    public List<Student> findStudent(@RequestBody @Valid SearchRequest searchRequest) throws Exception{
        List<Student>list=studentService.findStudent(searchRequest.getSearchKey(),searchRequest.getSearchValue());
        return list;

    }
}
