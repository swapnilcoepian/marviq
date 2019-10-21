package com.swapnil.marviq.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MachineEfficiencyController {

    @RequestMapping(value = "/oee", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void getOEE(LocalDateTime date) {

    }
}
