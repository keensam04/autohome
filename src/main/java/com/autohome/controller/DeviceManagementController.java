package com.autohome.controller;

import com.autohome.model.Switch;
import com.autohome.service.DeviceStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/room/{roomId}/device/{deviceId}/operate")
public class DeviceManagementController {

    @Autowired
    DeviceStateService deviceStateService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> switchOnOff(@PathVariable int roomId, @PathVariable int deviceId, @RequestBody Switch _switch){

        int noOfRows =deviceStateService.switchOnOff(roomId, deviceId, _switch,"John Doe");
        if(noOfRows>0)
              return ResponseEntity.ok().build();
          else if(noOfRows == 0)
              return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
          else
              return ResponseEntity.badRequest().build();
    }
}
