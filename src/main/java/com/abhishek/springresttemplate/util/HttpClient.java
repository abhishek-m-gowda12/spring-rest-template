package com.abhishek.springresttemplate.util;

import com.abhishek.springresttemplate.dto.EmployeeResponse;
import com.abhishek.springresttemplate.dto.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient {

    private final RestTemplate restTemplate = new RestTemplate();

//    public HttpClient(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    public Employee getStudent() {
        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.getForEntity("http://dummy.restapiexample.com/api/v1/employee/1", EmployeeResponse.class);
        return responseEntity.getBody().getData();
    }
}
