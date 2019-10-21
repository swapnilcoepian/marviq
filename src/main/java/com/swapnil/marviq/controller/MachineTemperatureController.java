package com.swapnil.marviq.controller;

import com.swapnil.marviq.service.MachineTemperatureService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;

@RestController
public class MachineTemperatureController {

    private static final LocalDateTime DEFAULT_DATE = LocalDateTime.of(2018, Month.JANUARY, 1, 0,0,0);

    private final MachineTemperatureService service;

    public MachineTemperatureController(MachineTemperatureService service) {
        this.service = service;
    }
    /**
     * ASSIGNMENT B : Status of the machine based on it’s core temperature over de last 24 hours.
     *
     * Use good/green as default
     * Use warning/orange if the temperature has been over 85, but under or equal to 100 degrees for less than 15 minutes.
     * Use fatal/red when [higher than 100 degrees] or [higher than 85 degrees for longer than 15 minutes].
     *
     * @param from
     * @return
     */
    @RequestMapping(value = "/temperatureStatusByMachine", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> getData(
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {

        // Default date range is 1-January to 2-January 2018 if user input date is missing
        from = from == null ? DEFAULT_DATE : from;
        LocalDateTime to = from.plusHours(24);

        Map<String, String> result = service.getData(from, to);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
