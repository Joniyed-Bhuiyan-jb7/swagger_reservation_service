package io.swagger.repository;

import io.swagger.model.Room;
import io.swagger.model.RoomType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
    public Optional<Room> findRoomByNumber(int number);
    public List<Room> findRoomByRoomType(RoomType roomType);
}
