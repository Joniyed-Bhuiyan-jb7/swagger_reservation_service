package io.swagger.api;

import io.swagger.exception.ResourceAlreadyExistsException;
import io.swagger.exception.ResourceNotExistsException;
import io.swagger.model.Reservation;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.model.Room;
import io.swagger.repository.ReservationsApi;
import io.swagger.service.ReservationService;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-05-12T06:30:05.493Z")

@Controller
public class ReservationsApiController implements ReservationsApi {

    private static final Logger log = LoggerFactory.getLogger(ReservationsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ReservationService reservationService;

    @org.springframework.beans.factory.annotation.Autowired
    public ReservationsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PostMapping
    public ResponseEntity<String> addReservation(@ApiParam(value = "Reservation object that needs to be added to the DB" ,required=true )  @Valid @RequestBody Reservation reservation) {
        try {
            Reservation reservation1 = new Reservation(reservation.getId(),reservation.getName(),reservation.getFromDate(),reservation.getToDate(),reservation.getRoom());
            Reservation saveReservation = reservationService.saveReservation(reservation1);
            return ResponseEntity.status(HttpStatus.CREATED).body("reserved the room...");
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Void> deleteReservation(@ApiParam(value = "reservation id to delete",required=true) @PathVariable("reservationId") int reservationId) {
        try {
            reservationService.deleteById(reservationId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Reservation>> findReservationBetweenGivenDate(@ApiParam(value = "exapmle: 2020-10-02",required=true) @PathVariable("startDate")  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @ApiParam(value = "exapmle: 2020-10-02",required=true) @PathVariable("endDate")  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            return ResponseEntity.ok().body(reservationService.findBetweenDate(startDate,endDate));
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<List<Reservation>> findReservations() {
        return ResponseEntity.ok().body(reservationService.findAll());
    }

    public ResponseEntity<Reservation> getReservationById(@ApiParam(value = "ID of reservation to return",required=true) @PathVariable("reservationId") int reservationId) {
        try {
            Reservation reservation = reservationService.findById(reservationId);
            return ResponseEntity.ok().body(reservation);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Void> updateReservationById(@ApiParam(value = "name that need to be updated",required=true) @PathVariable("reservationId") int reservationId,@ApiParam(value = "Updated reservation object" ,required=true )  @Valid @RequestBody Reservation reservation) {
        try {
            reservationService.updateReservation(reservationId,reservation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
