package com.swapnil.marviq.service;

import com.swapnil.marviq.model.ProductionData;
import com.swapnil.marviq.model.RuntimeData;
import com.swapnil.marviq.model.response.EfficiencyResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Calculates machine efficiency attributes
 */
@Service
public class MachineEfficiencyService {

    private final MachineStatusService service;

    public MachineEfficiencyService(MachineStatusService service) {
        this.service = service;
    }

    public List<EfficiencyResponse> getData(LocalDateTime from, LocalDateTime to) {
        // fetch data from dao
        List<ProductionData> prodData = service.getProdData(from, to);
        Map<String, RuntimeData> runData = service.getRunData(from, to);

        List<EfficiencyResponse> rows = new ArrayList<>();

        /* Now, reuse methods from MachineTemperatureService & MachineStatusService to calculate oee.
         But, make sure to re-factor those methods so that csv files don't get read more than once */


        //  Performance% = Reuse getNetProduction() & getScrapPercentage() method from MachineStatusService

        // Availability% = Reuse getDowntimePercentage() from MachineStatusService to calculate availability

        // Quality% = Similar to Performance%

        return rows;
    }
}
