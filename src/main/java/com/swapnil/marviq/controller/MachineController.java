package com.swapnil.marviq.controller;

import com.swapnil.marviq.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MachineController {

    @Autowired
    private MachineService service;

    @RequestMapping("/getData")
    public void getData(LocalDateTime date) {

    }
}
