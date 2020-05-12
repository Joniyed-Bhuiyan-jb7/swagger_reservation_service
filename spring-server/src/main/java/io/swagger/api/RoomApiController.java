package io.swagger.api;

import io.swagger.exception.ResourceNotExistsException;
import io.swagger.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.model.RoomType;
import io.swagger.repository.RoomApi;
import io.swagger.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-05-12T06:30:05.493Z")

@Controller
public class RoomApiController implements RoomApi {

    private static final Logger log = LoggerFactory.getLogger(RoomApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private RoomService roomService;

    @org.springframework.beans.factory.annotation.Autowired
    public RoomApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createRoom(@ApiParam(value = "Created room object" ,required=true )  @Valid @RequestBody Room room) {
        try {
            roomService.saveRoom(room);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ResourceNotExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> deleteRoom(@ApiParam(value = "The id that needs to be deleted",required=true) @PathVariable("roomId") int roomId) {
        try {
            roomService.deleteRoomById(roomId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Room>> getAllRoom() {
        return ResponseEntity.ok().body(roomService.findAll());
    }

    public ResponseEntity<Room> getRoomById(@ApiParam(value = "The id that needs to be fetched room info. ",required=true) @PathVariable("roomId") int roomId) {
        try {
            Room room = roomService.findById(roomId);
            return ResponseEntity.ok().body(room);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Room> getRoomByNumber(@ApiParam(value = "The type that needs to be fetched room info. ",required=true) @PathVariable("roomNumber") int roomNumber){
        try {
            Room room = roomService.findRoomByNumber(roomNumber);
            return ResponseEntity.ok().body(room);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Room>> getRoomByType(@ApiParam(value = "The type that needs to be fetched room info. ",required=true) @PathVariable("roomType") RoomType roomType) {
        try {
            List<Room> room = roomService.findRoomByType(roomType);
            return ResponseEntity.ok().body(room);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> updateRoom(@ApiParam(value = "name that need to be updated",required=true) @PathVariable("roomId") int roomId,@ApiParam(value = "Updated room object" ,required=true )  @Valid @RequestBody Room room) {
        try {
            roomService.updateRoom(roomId,room);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
