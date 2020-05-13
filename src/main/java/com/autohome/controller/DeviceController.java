package com.autohome.controller;

import com.autohome.config.IsAdmin;
import com.autohome.model.Device;
import com.autohome.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room/{roomId}/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Device> getDevices(@PathVariable int roomId) {
        return deviceService.getDevices(roomId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> addDevice(@PathVariable int roomId,
                                            @RequestBody Device device, UriComponentsBuilder uriBuilder) {
        int deviceId = deviceService.addDevice(roomId, device);

        if (deviceId > 0) {
            URI uri = uriBuilder.path("room/").path(String.valueOf(roomId)).path("/device/").path(String.valueOf(deviceId)).build().toUri();
            return ResponseEntity.created(uri).build();
        } else if (deviceId == -1) {
            return ResponseEntity.badRequest().body(String.format("Device with name %s already exists in this room", device.getDeviceName()));

        } else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @RequestMapping(value = "/{deviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Device getDevice(@PathVariable int roomId, @PathVariable int deviceId) {
        return deviceService.getDevice(roomId, deviceId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateDevice(@PathVariable int roomId, @PathVariable int id, @RequestBody Device device) {
        boolean isSuccess = deviceService.updateDevice(roomId, id, device);
        if (isSuccess) {
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.unprocessableEntity().build();
    }

    @RequestMapping(value = "/{id}/changeroom", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Device> changeRoom(@PathVariable int roomId, @PathVariable int id, @RequestParam(required = true) int newRoomId, UriComponentsBuilder uriBuilder) {
        Optional<Device> device =  deviceService.changeRoom(roomId, id, newRoomId);
        if (device.isPresent()) {
            URI uri = uriBuilder.path("/room/").path(String.valueOf(newRoomId)).path("/device/").path(String.valueOf(id)).build().toUri();
            return ResponseEntity.ok().location(uri).body(device.get());
        }
        else
            return ResponseEntity.badRequest().build();

/*        return device.map(d -> {
            URI uri = uriBuilder.path("/room/").path(String.valueOf(newRoomId)).path("/device/").path(String.valueOf(id)).build().toUri();
            return ResponseEntity.ok().location(uri).body(device.get());
        }).orElseGet(() -> ResponseEntity.badRequest().build());*/

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> offBoardDevice(@PathVariable int roomId,@PathVariable int id){
        int noOfRows = deviceService.offBoardDevice(roomId,id);
        if (noOfRows > 0){
            return ResponseEntity.noContent().build();
        }
        else if (noOfRows == 0){
            return ResponseEntity.notFound().build();
        }
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
