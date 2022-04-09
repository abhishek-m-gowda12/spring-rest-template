package com.abhishek.springresttemplate.http.response;

import com.abhishek.springresttemplate.dto.Employee;
import lombok.Data;

@Data
public class EmployeeResponse {
    private String status;
    private Employee data;
    private String message;
}
