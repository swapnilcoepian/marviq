package com.swapnil.marviq.service;

import com.swapnil.marviq.dao.ProductionDao;
import com.swapnil.marviq.model.DataPerHour;
import com.swapnil.marviq.model.ProductionData;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MachineTemperatureService {

    private final ProductionDao prodDao;

    public MachineTemperatureService(ProductionDao prodDao) {
        this.prodDao = prodDao;
    }

    /**
     * Indicate status of the machine based on it’s core temperature over de last 24 hours.
     *
     * @param from  (required)
     * @param to    (required)
     * @return
     */
    public Map<String, String> getData(LocalDateTime from, LocalDateTime to) {
        List<ProductionData>  data = getProdData(from, to);

        Map<String, String> rows = new HashMap<>();
        for (ProductionData element : data) {

            String machine = element.getMachineName();
            String warning = "good/green";

            // Counter to measure if the temperature has been over 85. 1 increment represents 5-mins
            int counter = 0;

            outer: for (DataPerHour dataPerHour : element.getDataPerHour().values()) {
                List<Integer> temps = dataPerHour.getTemp();

                for (int temp : temps) {
                    if (temp < 85) {
                        // counter resets if machine temperature falls below 85
                        counter = 0;
                    } else if (temp > 100 || counter == 3) {
                        warning = "fatal/red";
                        break outer;
                    } else {
                        // As soon as temperature goes above 85, status will be warning/orange
                        warning = "warning/orange";
                        counter++;
                    }
                }
            }

            rows.put(machine,warning);
        }

        return rows;
    }

    /**
     * TODO Duplicate code, same as MachineStatusService.getProdData()
     */
    private List<ProductionData> getProdData(LocalDateTime from, LocalDateTime to) {
        List<ProductionData> data = null;
        try {
            data = prodDao.getData(from, to);
        } catch (IOException e) {
            // Throw generic exception
        }

        return data;
    }
}
