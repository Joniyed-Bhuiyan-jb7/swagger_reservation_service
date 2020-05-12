package io.swagger.service;

import io.swagger.exception.ResourceAlreadyExistsException;
import io.swagger.exception.ResourceNotExistsException;
import io.swagger.model.Reservation;
import io.swagger.model.Room;
import io.swagger.repository.ReservationRepository;
import io.swagger.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository,RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    // TODO add necessary methods here

    public List<Reservation> findAll(){
        List<Reservation> reservationList = new ArrayList<>();
        reservationRepository.findAll().forEach(reservationList::add);
        return reservationList;
    }


    public Reservation findById(int id) throws ResourceNotExistsException {
        if(reservationRepository.exists(id)){
            Reservation reservation = reservationRepository.findOne(id);
            return reservation;
        }else{
            throw new ResourceNotExistsException(id+"");
        }
    }

    public Reservation saveReservation(Reservation reservation) throws ResourceAlreadyExistsException {
        if(!reservationRepository.exists(reservation.getId()) && roomRepository.exists(reservation.getRoom().getId())){
            return reservationRepository.save(reservation);
        }else{
            throw new ResourceAlreadyExistsException(reservation.getId()+"");
        }
    }

    public void deleteAll(){
        reservationRepository.deleteAll();
    }

    public void deleteById(int id) throws ResourceNotExistsException {
        if(reservationRepository.exists(id)){
            reservationRepository.delete(id);
        }else{
            throw new ResourceNotExistsException(id+"");
        }
    }

    public Reservation updateReservation(int id, Reservation reservation) throws ResourceNotExistsException {
        if(reservationRepository.exists(id) && roomRepository.exists(reservation.getRoom().getId())){
            reservation.setId(id);
            Reservation savedReservation = reservationRepository.save(reservation);
            return savedReservation;
        }else{
            throw new ResourceNotExistsException(id+"");
        }
    }

    public List<Reservation> findBetweenDate(LocalDate start,LocalDate end) throws ResourceNotExistsException {
         List<Reservation> reservationList = reservationRepository.findReservationByFromDateBetweenOrToDateBetween(start,end,start,end);
         List<Reservation> reservationList1 = reservationRepository.findReservationByFromDateBeforeAndToDateAfter(start,end);
         List<Room> roomList = new ArrayList<>();
         if(!reservationList.isEmpty() || !reservationList1.isEmpty()){
              reservationList.addAll(reservationList1);
              return reservationList;
         }else{
             throw new ResourceNotExistsException("no resevation available between this range...");
         }
    }

}
