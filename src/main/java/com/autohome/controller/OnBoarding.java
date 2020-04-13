package com.autohome.controller;

import com.autohome.model.Room;
import com.autohome.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/onboard")
public class OnBoarding {

    @Autowired
    RoomService roomService;

    //Add and delete rooms and devices

    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public ResponseEntity<Object> addRooms(@RequestBody Room room, UriComponentsBuilder uriBuilder) {

        boolean isSuccess = roomService.addRoom(room);
        if(isSuccess) {
            URI uri = uriBuilder.path("/room/").path(room.getRoomName()).build().toUri();
            return ResponseEntity.created(uri).build();
        }

        return ResponseEntity.unprocessableEntity().build();


    }

    @RequestMapping(value = "/room/{roomName}", method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Room getRoom(@PathVariable String roomName){
        return roomService.getRoom(roomName);
    }


}
