package com.hrd.__HAK_KIMHENG_KPS_SPRING_HOMEWORK001.controller;

import com.hrd.__HAK_KIMHENG_KPS_SPRING_HOMEWORK001.dto.CreateTicketReqDto;
import com.hrd.__HAK_KIMHENG_KPS_SPRING_HOMEWORK001.dto.UpdatePaymentRequest;
import com.hrd.__HAK_KIMHENG_KPS_SPRING_HOMEWORK001.model.APIResponseListTicket;
import com.hrd.__HAK_KIMHENG_KPS_SPRING_HOMEWORK001.model.Ticket;
import com.hrd.__HAK_KIMHENG_KPS_SPRING_HOMEWORK001.model.TicketStatus;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/tickets")
public class Controller {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    static int i = 0;
    ArrayList<Ticket> listTicket = new ArrayList<>();

    @GetMapping
    @Operation(summary = "Get All Tickets")
    public ResponseEntity<APIResponseListTicket<List<Ticket>>> getAllTickets(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if ((page != null && page <= 0) || (size != null && size <= 0)) {
            return new ResponseEntity<>(
                    new APIResponseListTicket<>(false, "Page and size must be greater than 0", HttpStatus.BAD_REQUEST, null, LocalDate.now()),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (page != null && size != null) {
            List<Ticket> list = new ArrayList<>();
            for (int i = (page - 1) * size; i < page * size; i++) {
                if (i < listTicket.size()) {
                    list.add(listTicket.get(i));
                }
            }
            return new ResponseEntity<>(
                    new APIResponseListTicket<>(true, "Successful", HttpStatus.OK, list, LocalDate.now()),
                    HttpStatus.OK
            );
        }
        else {
            return new ResponseEntity<>(
                    new APIResponseListTicket<>(true, "Successful", HttpStatus.OK, listTicket, LocalDate.now()),
                    HttpStatus.OK
            );
        }
    }

    @PutMapping
    @Operation(summary = "Bulk Update Payment Status For Multiple Tickets")
    public ResponseEntity<APIResponseListTicket<List<Ticket>>> updatePayment(@RequestBody UpdatePaymentRequest request) {
        List<Ticket> updatedTickets = new ArrayList<>();
        boolean ticketFound = false;
        for (Ticket ticket : listTicket) {
            if (request.getTicketIds().contains(ticket.getTicketId())) {
                ticket.setPaymentStatus(request.isStatus());
                updatedTickets.add(ticket);
                ticketFound = true;
            }
        }
        if (!ticketFound) {
            return new ResponseEntity<>(
                    new APIResponseListTicket<>(false, "Tickets not found", HttpStatus.NOT_FOUND, null, LocalDate.now()),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                new APIResponseListTicket<>(true, "Payment status updated", HttpStatus.OK, updatedTickets, LocalDate.now()),
                HttpStatus.OK
        );
    }
    @PostMapping
    @Operation(summary = "Create a new Ticket")
    public ResponseEntity<APIResponseListTicket<Ticket>>  addTicket(@RequestBody CreateTicketReqDto createTicketReqDto) {
        Ticket ticket;
        try {
            if(createTicketReqDto.getPassengerName()== null) throw new NullPointerException();

            ticket = new Ticket(
                    ++i,
                    createTicketReqDto.getPassengerName(),
                    createTicketReqDto.getTravelDate(),
                    createTicketReqDto.getSourceStation(),
                    createTicketReqDto.getDestinationStation(),
                    createTicketReqDto.getPrice(),
                    createTicketReqDto.getPayment(),
                    createTicketReqDto.getTicketStatus(),
                    createTicketReqDto.getSeatNumber());
            listTicket.add(ticket);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(
                    new APIResponseListTicket<>(false, "Field required can't null! ", HttpStatus.BAD_REQUEST, null, LocalDate.now()),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                new APIResponseListTicket<>(true, "successful", HttpStatus.OK, ticket, LocalDate.now()),
                HttpStatus.OK
        );


    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Ticket by Id")
    public ResponseEntity<APIResponseListTicket<List<Ticket>>> getTicketById(@RequestParam int id){
        List<Ticket> ticket = new ArrayList<>();
        for (Ticket t : listTicket) {
            if (t.getTicketId() == id) {
                ticket.add(t);
                return new ResponseEntity<>(new APIResponseListTicket<>(true, "successful", HttpStatus.OK, ticket, LocalDate.now()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new APIResponseListTicket<>(false, "Ticket not found", HttpStatus.NOT_FOUND, null, LocalDate.now()), HttpStatus.NOT_FOUND);

    }
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Ticket by Id")
    public ResponseEntity<APIResponseListTicket<Ticket>> updateById(@RequestParam int id , @RequestBody CreateTicketReqDto createTicketReqDto) {
        for (Ticket t : listTicket) {
            if (t.getTicketId() == id) {
                t.setPassengerName(createTicketReqDto.getPassengerName());
                t.setTravelDate(createTicketReqDto.getTravelDate());
                t.setTravelDate(createTicketReqDto.getTravelDate());
                t.setSourceStation(createTicketReqDto.getSourceStation());
                t.setDestinationStation(createTicketReqDto.getDestinationStation());
                t.setPrice(createTicketReqDto.getPrice());
                t.setPaymentStatus(createTicketReqDto.getPayment());
                t.setTicketStatus(createTicketReqDto.getTicketStatus());
                t.setSeatNumber(createTicketReqDto.getSeatNumber());
                return new ResponseEntity<>(
                        new APIResponseListTicket<>(true, "successful", HttpStatus.OK, t, LocalDate.now()),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(
                new APIResponseListTicket<>(false, "Ticket not found", HttpStatus.NOT_FOUND, null, LocalDate.now()),
                HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Ticket by Id")
    public ResponseEntity<APIResponseListTicket<Ticket>> deleteTicketById (@RequestParam int id){
        for (Ticket t : listTicket){
            if (t.getTicketId()== id){
                listTicket.remove(t);
                return new ResponseEntity<>(
                        new APIResponseListTicket<>(true, "successful", HttpStatus.OK, t, LocalDate.now()),
                        HttpStatus.OK
                );
            }
        }
        return new ResponseEntity<>(
                new APIResponseListTicket<>(false, "Id Not found", HttpStatus.NOT_FOUND, null, LocalDate.now()),
                HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/bulk")
    @Operation(summary = "Bulk Create Ticket")
    public ResponseEntity<APIResponseListTicket<List<Ticket>>> addBulkTicket(@RequestBody List<CreateTicketReqDto> createTicketReqDtoList){
        List<Ticket> list = new ArrayList<>();
        for (CreateTicketReqDto createTicketReqDto : createTicketReqDtoList) {
            Ticket ticket = new Ticket(
                    ++i,
                    createTicketReqDto.getPassengerName(),
                    createTicketReqDto.getTravelDate(),
                    createTicketReqDto.getSourceStation(),
                    createTicketReqDto.getDestinationStation(),
                    createTicketReqDto.getPrice(),
                    createTicketReqDto.getPayment(),
                    createTicketReqDto.getTicketStatus(),
                    createTicketReqDto.getSeatNumber());

            listTicket.add(ticket);
            list.add(ticket);
        }
        return new ResponseEntity<>(
                new APIResponseListTicket<>(true, "successful", HttpStatus.OK, list, LocalDate.now()),
                HttpStatus.OK
        );
    }



    @GetMapping("/search")
    @Operation(summary = "Search ticket by passenger name")
    public ResponseEntity<APIResponseListTicket<List<Ticket>>> searchTicket(@RequestParam String passengerName){
        List<Ticket> ticket = listTicket.stream()
                .filter(t -> t.getPassengerName().toLowerCase().contains(passengerName.toLowerCase()))
                .toList();
        if(!ticket.isEmpty()){
            return new ResponseEntity<>(
                    new APIResponseListTicket<>(true, "Successful", HttpStatus.OK, ticket, LocalDate.now()),
                    HttpStatus.OK);

        }
        return new ResponseEntity<>(
                new APIResponseListTicket<>(false, "Id not found!", HttpStatus.NOT_FOUND, null, LocalDate.now()),
                HttpStatus.NOT_FOUND);
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter ticket by ticket status and date travel")
    public ResponseEntity<APIResponseListTicket<List<Ticket>>> filterTicket(@RequestParam TicketStatus ticketStatus , @RequestParam LocalDate dateTravel){
        List<Ticket> listFilter = listTicket.stream()
                .filter(ticket -> ticket.getTicketStatus() == ticketStatus && ticket.getTravelDate().equals(dateTravel))
                .toList();

        if(listFilter.isEmpty()){
            return new ResponseEntity<>(
                    new APIResponseListTicket<>(true,"Successful",HttpStatus.OK,listFilter,LocalDate.now()),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new APIResponseListTicket<>(false, "Id not found!", HttpStatus.NOT_FOUND, null, LocalDate.now()),
                HttpStatus.NOT_FOUND);
    }


}

