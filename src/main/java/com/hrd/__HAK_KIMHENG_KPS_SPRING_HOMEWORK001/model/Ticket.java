package com.hrd.__HAK_KIMHENG_KPS_SPRING_HOMEWORK001.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
    private int ticketId;
    private String passengerName;
    private LocalDate travelDate;
    private String sourceStation;
    private String destinationStation;
    private int price;
    private boolean paymentStatus;
    private TicketStatus ticketStatus;
    private int seatNumber;
}
