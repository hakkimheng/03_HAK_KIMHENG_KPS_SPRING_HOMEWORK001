package com.hrd.__HAK_KIMHENG_KPS_SPRING_HOMEWORK001.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseListTicket<T> {
    private boolean success;
    private String message;
    private HttpStatus httpStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private LocalDate timestamp;

}
