package com.hrd.__HAK_KIMHENG_KPS_SPRING_HOMEWORK001.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hrd.__HAK_KIMHENG_KPS_SPRING_HOMEWORK001.model.TicketStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class CreateTicketReqDto {
    private String passengerName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate travelDate;
    private String sourceStation;
    private String destinationStation;
    private int price;
    private boolean paymentStatus;
    private TicketStatus ticketStatus;
    private int seatNumber;

    public boolean getPayment(){
        return paymentStatus;
    }

}
