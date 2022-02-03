package com.firstmilestone.first.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {

    private HttpStatus status;
    private String job;
    private String data;
}
