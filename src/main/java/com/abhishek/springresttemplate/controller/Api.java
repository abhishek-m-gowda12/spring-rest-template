package com.abhishek.springresttemplate.controller;

import com.abhishek.springresttemplate.dto.Employee;
import com.abhishek.springresttemplate.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/student")
public class Api {
    private final StudentService studentService;

    public Api(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    private ResponseEntity<Employee> getStudent() {
        Employee student = studentService.getStudent();
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }
}
