package com.autohome.controller;

import com.autohome.model.Device;
import com.autohome.model.Room;
import com.autohome.service.DeviceService;
import com.autohome.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class BaseController {

    @Autowired
    RoomService roomService;

    @Autowired
    DeviceService deviceService;

    @RequestMapping(value = "/room", method = RequestMethod.POST)
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

    @RequestMapping(value = "/room/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Room getRoom(@PathVariable int id) {
        return roomService.getRoom(id);
    }

    @RequestMapping(value = "/rooms",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Room> getRooms(){
        return roomService.getRooms();
    }


    @RequestMapping(value = "/room/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateRoom(@RequestBody Room room,@PathVariable int id, UriComponentsBuilder uriBuilder){
        boolean isSuccess = roomService.updateRoom(room,id);
        if(isSuccess) {
            URI uri = uriBuilder.path("/room/").path(room.getRoomName()).build().toUri();
            return ResponseEntity.created(uri).build();
        }
        else
            return ResponseEntity.unprocessableEntity().build();
    }

    @RequestMapping(value = "/room/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteRoom(@PathVariable int id, UriComponentsBuilder uriBuilder){
        boolean isSuccess = roomService.deleteRoom(id);
        if(isSuccess){
            URI uri = uriBuilder.path("/room/").path(String.format("%d",id)).build().toUri();
            return ResponseEntity.created(uri).build();
        }
        else
            return ResponseEntity.unprocessableEntity().build();
    }

    @RequestMapping(value = "/room/{roomId}/device", method = RequestMethod.POST)
    public ResponseEntity<Object> addDevice(@PathVariable int roomId,
                                            @RequestBody Device device, UriComponentsBuilder uriBuilder) {
        int deviceId = deviceService.addDevice(roomId, device);

        if(deviceId > 0){
            URI uri = uriBuilder.path("room/").path(String.valueOf(roomId)).path("/device/").path(String.valueOf(deviceId)).build().toUri();
            return ResponseEntity.created(uri).build();
        } else if (deviceId == -1) {
            return ResponseEntity.badRequest().body(String.format("Device with name %s already exists in this room", device.getDeviceName()));

        }
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @RequestMapping(value = "/room/{roomId}/device/{deviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Device getDevice(@PathVariable int roomId, @PathVariable int deviceId){
        return deviceService.getDevice(roomId,deviceId);
    }


    @RequestMapping(value = "/room/{roomId}/devices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Device> getDevices(@PathVariable int roomId){
        return deviceService.getDevices(roomId);
    }

    @RequestMapping(value = "/room/{roomId}/device/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Object> updateDevice(@PathVariable int roomId,@PathVariable int id,@RequestBody Device device, UriComponentsBuilder uriBuilder){
        boolean isSuccess = deviceService.updateDevice(roomId, id, device);
        if(isSuccess){
            URI uri = uriBuilder.path("room/").path(String.valueOf(roomId)).path("/device/").path(String.valueOf(id)).build().toUri();
            return ResponseEntity.created(uri).build();
        }
        else
            return ResponseEntity.unprocessableEntity().build();
    }


}
