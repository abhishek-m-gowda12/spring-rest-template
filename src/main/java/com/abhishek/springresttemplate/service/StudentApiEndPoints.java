package com.abhishek.springresttemplate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class StudentApiEndPoints {
    public StudentApiEndPoints(
            @Value("${student.base.url}") String studentBaseUrl,
            @Value("${student.by.id.endpoint}") String studentByIdEndPoint,
            @Value("${student.all.endpoint}") String studentGetAllEndPoint,
            @Value("${student.create.endpoint}") String studentCreateEndPoint,
            @Value("${student.edit.by.id.endpoint}") String studentEditByIdEndPoint,
            @Value("${student.delete.by.id.endpoint}") String studentDeleteByIdEndPoint) {
        this.studentBaseUrl = studentBaseUrl;
        this.studentByIdEndPoint = studentByIdEndPoint;
        this.studentGetAllEndPoint = studentGetAllEndPoint;
        this.studentCreateEndPoint = studentCreateEndPoint;
        this.studentEditByIdEndPoint = studentEditByIdEndPoint;
        this.studentDeleteByIdEndPoint = studentDeleteByIdEndPoint;
    }

    private final String studentBaseUrl;

    private final String studentByIdEndPoint;

    private final String studentGetAllEndPoint;

    private final String studentCreateEndPoint;

    private final String studentEditByIdEndPoint;

    private final String studentDeleteByIdEndPoint;

    public String getStudentBaseUrl() {
        return studentBaseUrl;
    }

    public String getStudentByIdEndPoint() {
        return studentByIdEndPoint;
    }

    public String getStudentGetAllEndPoint() {
        return studentGetAllEndPoint;
    }

    public String getStudentCreateEndPoint() {
        return studentCreateEndPoint;
    }

    public String getStudentEditByIdEndPoint() {
        return studentEditByIdEndPoint;
    }

    public String getStudentDeleteByIdEndPoint() {
        return studentDeleteByIdEndPoint;
    }
}
