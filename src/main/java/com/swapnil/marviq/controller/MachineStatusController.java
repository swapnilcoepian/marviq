package com.swapnil.marviq.controller;

import com.swapnil.marviq.model.response.NetProductionResponse;
import com.swapnil.marviq.model.response.ProductionPerHourResponse;
import com.swapnil.marviq.model.response.PercentageResponse;
import com.swapnil.marviq.service.MachineStatusService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@RestController
public class MachineStatusController {

    private static final LocalDateTime DEFAULT_DATE = LocalDateTime.of(2018, Month.JANUARY, 1, 0,0,0);

    private final MachineStatusService service;

    public MachineStatusController(MachineStatusService service) {
        this.service = service;
    }

    /**
     * Welcome Page
     * @return String
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> test() {
        return new ResponseEntity("Welcome to Marviq project", HttpStatus.OK);
    }

    /**
     * The net production for the machine (gross output - scrap)
     * @param from  (optional)
     * @return List<NetProductionResponse>
     */
    @RequestMapping(value = "/netProduction", method = RequestMethod.GET)
    public ResponseEntity<List<NetProductionResponse>> getNetProduction(
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {

        // Default date range is 1-January to 2-January 2018 if user input date is missing
        from = from == null ? DEFAULT_DATE : from;
        LocalDateTime to = from.plusHours(24);

        List<NetProductionResponse> result = service.getNetProduction(from, to);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * The percentage of scrap vs gross production
     * @param from  (optional)
     */
    @RequestMapping(value = "/scrapPercentage", method = RequestMethod.GET)
    public ResponseEntity<List<PercentageResponse>> getScrapPercentage(
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {

        from = from == null ? DEFAULT_DATE : from;
        LocalDateTime to = from.plusHours(24);

        List<PercentageResponse> result = service.getScrapPercentage(from, to);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * The percentage of downtime for a machine.
     * @param from  (optional)
     */
    @RequestMapping(value = "/downtimePercentage", method= RequestMethod.GET)
    public ResponseEntity<List<PercentageResponse>> getDowntimePercentage(
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {

        from = from == null ? DEFAULT_DATE : from;
        LocalDateTime to = from.plusHours(24);

        List<PercentageResponse> result = service.getDowntimePercentage(from, to);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * The net production (gross production – scrap) for every hour
     * @param from  (optional)
     */
    @RequestMapping(value = "/productionByHour", method = RequestMethod.GET)
    public ResponseEntity<List<ProductionPerHourResponse>> getProductionByHour(
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {

        from = from == null ? DEFAULT_DATE : from;
        LocalDateTime to = from.plusHours(24);

        List<ProductionPerHourResponse> result = service.getProductionByHour(from, to);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
