package com.abhishek.springresttemplate.service;

import com.abhishek.springresttemplate.dto.Employee;
import com.abhishek.springresttemplate.util.HttpClient;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final HttpClient httpClient;

    public StudentService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Employee getStudent() {
        return httpClient.getStudent();
    }
}
