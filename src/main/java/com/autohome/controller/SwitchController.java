package com.autohome.controller;

import com.autohome.model.Device;
import com.autohome.service.SwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/room/{roomid}")
public class SwitchController {

    @Autowired
    SwitchService switchService;

    @RequestMapping(value = "/device/{state}",method = RequestMethod.GET)
    public ResponseEntity<Object> switchOnOff(@PathVariable int roomId,@PathVariable int state,@RequestBody Device device){
          if(switchService.switchOnOff(roomId, state, device))
              return ResponseEntity.ok().build();
          else
              return ResponseEntity.badRequest().build();
    }
}
