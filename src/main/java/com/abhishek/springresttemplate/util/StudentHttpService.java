package com.abhishek.springresttemplate.util;

import com.abhishek.springresttemplate.dto.Employee;
import com.abhishek.springresttemplate.http.response.EmployeeResponse;
import com.abhishek.springresttemplate.http.AbstractHttpClient;
import com.abhishek.springresttemplate.http.HttpServiceContext;
import com.abhishek.springresttemplate.service.StudentApiEndPoints;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class StudentHttpService extends AbstractHttpClient {

    public StudentHttpService(
            @Qualifier("demoRestTemplate") RestTemplate restTemplate,
            StudentApiEndPoints studentApiEndPoints) {
        super(restTemplate);
        this.studentApiEndPoints = studentApiEndPoints;
    }

    private final StudentApiEndPoints studentApiEndPoints;

    public Employee getStudent() {
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("studentId", "1");

        HttpServiceContext httpServiceContext = HttpServiceContext.builder()
                .baseUri(studentApiEndPoints.getStudentBaseUrl())
                .path(studentApiEndPoints.getStudentByIdEndPoint())
                .pathParams(pathParams)
                .build();

        ResponseEntity<EmployeeResponse> responseEntity = get(httpServiceContext, EmployeeResponse.class);

        return responseEntity.getBody().getData();
    }
}


