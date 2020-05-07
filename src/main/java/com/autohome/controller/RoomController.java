package com.autohome.controller;

import com.autohome.model.Room;
import com.autohome.service.RoomService;
import com.autohome.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @Secured({Constants.ROLE_USER, Constants.ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Room> getRooms(){
        return roomService.getRooms();
    }

    @Secured({Constants.ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> addRoom(@RequestBody Room room, UriComponentsBuilder uriBuilder) {

        int roomId = roomService.addRoom(room);
        if (roomId>0) {
            URI uri = uriBuilder.path("/room/").path(room.getRoomName()).build().toUri();
            return ResponseEntity.created(uri).build();
        }
        else if (roomId == -1)
            return ResponseEntity.badRequest().body(String.format("Room with name %s already exists in this room",room.getRoomName()));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Room getRoom(@PathVariable int id) {
        return roomService.getRoom(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateRoom(@RequestBody Room room,@PathVariable int id, UriComponentsBuilder uriBuilder){
        boolean isSuccess = roomService.updateRoom(room,id);
        if(isSuccess) {
            URI uri = uriBuilder.path("/room/").path(room.getRoomName()).build().toUri();
            return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.unprocessableEntity().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteRoom(@PathVariable int id){
        boolean isSuccess = roomService.deleteRoom(id);
        if(isSuccess){
            return ResponseEntity.noContent().build();
        }
        else
            return ResponseEntity.unprocessableEntity().build();
    }
}
