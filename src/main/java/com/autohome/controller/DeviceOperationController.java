package com.autohome.controller;

import com.autohome.auth.IsAdmin;
import com.autohome.auth.IsUser;
import com.autohome.model.ErrorMessage;
import com.autohome.model.Switch;
import com.autohome.service.DeviceStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(value = "/room/{roomId}/device/{deviceId}/operate")
public class DeviceOperationController {

    @Autowired
    DeviceStateService deviceStateService;

    @RequestMapping(method = RequestMethod.POST)
    @IsUser
    @IsAdmin
    public ResponseEntity<Object> switchOnOff(@PathVariable int roomId, @PathVariable int deviceId, @RequestBody Switch _switch){

        int noOfRows = deviceStateService.switchOnOff(roomId, deviceId, _switch,"John Doe");
        if(noOfRows>0)
              return ResponseEntity.ok().build();
          else if(noOfRows == -2)
              return ResponseEntity.notFound().build();
          else if(noOfRows == -1) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMsg("switch not toggled");
            errorMessage.setCause(Collections.singletonList("device state is already " + _switch.isOn()));
            return ResponseEntity.badRequest().body(errorMessage);
        }
          else
              return ResponseEntity.badRequest().build();
    }
}
