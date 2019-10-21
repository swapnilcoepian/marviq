package com.swapnil.marviq.controller;

import com.swapnil.marviq.model.response.EfficiencyResponse;
import com.swapnil.marviq.service.MachineEfficiencyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@RestController
public class MachineEfficiencyController {

    private static final LocalDateTime DEFAULT_DATE = LocalDateTime.of(2018, Month.JANUARY, 1, 0,0,0);

    private final MachineEfficiencyService service;

    public MachineEfficiencyController(MachineEfficiencyService service) {
        this.service = service;
    }

    /**
     * Calculate the OEE (Overall Equipment Efficiency) for every machine over the last 24 hours.
     * An OEE is a percentage calculated by multiplying 3 percentages:
     *
     * Performance% = actual gross production / norm gross production * 100%
     * (Assume a norm gross production of 30.000 bricks per hour)
     *
     * Availability% = actual uptime / norm uptime * 100%
     * (Assume a norm uptime of 75% (is 16 hours / 24 hours).)
     *
     * Quality% = (actual gross production - actual scrap) / actual gross production * 100%
     *
     * @param from  (optional)
     */
    @RequestMapping(value = "/oee", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EfficiencyResponse>> getOEE(
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {
        from = from == null ? DEFAULT_DATE : from;
        LocalDateTime to = from.plusHours(24);

        List<EfficiencyResponse> result = service.getData(from, to);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
