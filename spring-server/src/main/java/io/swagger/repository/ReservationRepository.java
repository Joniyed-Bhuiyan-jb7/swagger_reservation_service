package io.swagger.repository;

import io.swagger.model.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    public List<Reservation> findReservationByFromDateBetween(LocalDate fromDate, LocalDate toDate);
    public List<Reservation> findReservationByFromDateBeforeAndToDateAfter(LocalDate fromDate, LocalDate toDate);
    public List<Reservation> findReservationByFromDateBetweenOrToDateBetween(LocalDate fromDate, LocalDate fromDateEnd, LocalDate toDate, LocalDate toDateEnd);
}
