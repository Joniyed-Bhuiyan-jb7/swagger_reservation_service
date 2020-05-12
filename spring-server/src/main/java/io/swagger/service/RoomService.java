package io.swagger.service;

import io.swagger.exception.ResourceNotExistsException;
import io.swagger.model.Room;
import io.swagger.model.RoomType;
import io.swagger.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // TODO add necessary methods heres

    public List<Room> findAll(){
        List<Room> roomList = new ArrayList<>();
        roomRepository.findAll().forEach(roomList::add);
        return roomList;
    }

    public Room findById(int id) throws ResourceNotExistsException {
        if(roomRepository.exists(id)){
            return roomRepository.findOne(id);
        }else{
            throw new ResourceNotExistsException(id+"");
        }
    }


    public Room saveRoom(Room room) throws ResourceNotExistsException {
        if(!roomRepository.exists(room.getId())){
            return roomRepository.save(room);
        }else {
            throw new ResourceNotExistsException(room.getId()+"");
        }
    }

    public Room updateRoom(int id, Room room) throws ResourceNotExistsException {
        if(roomRepository.exists(id)){
            room.setId(id);
            return roomRepository.save(room);
        }else{
            throw new ResourceNotExistsException(id+" ");
        }
    }

    public void deleteRoomById(int id) throws ResourceNotExistsException {
        if(roomRepository.exists(id)){
            roomRepository.delete(id);
        }else{
            throw new ResourceNotExistsException(id+"");
        }
    }

    public void deleteAll(){
        roomRepository.deleteAll();
    }


    public Room findRoomByNumber(int number) throws ResourceNotExistsException {
        if(roomRepository.findRoomByNumber(number).isPresent()){
            return roomRepository.findRoomByNumber(number).get();
        }
        else{
            throw new ResourceNotExistsException(number+"");
        }
    }

    public List<Room> findRoomByType(RoomType roomType) throws ResourceNotExistsException {
        if(!roomRepository.findRoomByRoomType(roomType).isEmpty()){
            return roomRepository.findRoomByRoomType(roomType);
        }else{
            throw new ResourceNotExistsException(roomType+"");
        }
    }

}
