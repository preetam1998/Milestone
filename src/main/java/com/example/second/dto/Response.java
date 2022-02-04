package com.example.second.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Response {
    private HttpStatus status;
    private String message;
    private String data;

}
