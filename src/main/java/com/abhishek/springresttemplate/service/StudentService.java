package com.abhishek.springresttemplate.service;

import com.abhishek.springresttemplate.dto.Employee;
import com.abhishek.springresttemplate.util.StudentHttpService;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentHttpService studentHttpService;

    public StudentService(StudentHttpService studentHttpService) {
        this.studentHttpService = studentHttpService;
    }

    public Employee getStudent() {
        return studentHttpService.getStudent();
    }
}
