package com.hrd.__HAK_KIMHENG_KPS_SPRING_HOMEWORK001.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentRequest {
    private List<Integer> ticketIds;
    private boolean status;
}