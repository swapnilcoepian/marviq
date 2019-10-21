package com.swapnil.marviq.service;

import com.swapnil.marviq.dao.RuntimeDao;
import com.swapnil.marviq.model.DataPerHour;
import com.swapnil.marviq.model.ProductionData;
import com.swapnil.marviq.dao.ProductionDao;
import com.swapnil.marviq.model.RuntimeData;
import com.swapnil.marviq.model.response.GenericResponse;
import com.swapnil.marviq.model.response.NetProductionResponse;
import com.swapnil.marviq.model.response.PercentageResponse;
import com.swapnil.marviq.model.response.ProductionPerHourResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Service layer to report the machine statuses
 */
@Service
public class MachineStatusService {

    private final ProductionDao prodDao;
    private final RuntimeDao runDao;

    public MachineStatusService(ProductionDao prodDao, RuntimeDao runDao) {
        this.prodDao = prodDao;
        this.runDao = runDao;
    }

    /**
     * Calculates The net production for the machine (gross output - scrap)
     * @param from  (required)
     * @param to    (required)
     * @return
     */
    public List<NetProductionResponse> getNetProduction(LocalDateTime from, LocalDateTime to) {
        List<ProductionData>  data = getProdData(from, to);

        List<NetProductionResponse> rows = new ArrayList<>();
        for(ProductionData element : data) {
            NetProductionResponse row = new NetProductionResponse();
            populateGeneralResponse(element.getMachineName(), from, to, row);

            int netProd = 0;
            for(DataPerHour dataPerHour : element.getDataPerHour().values()) {
                netProd = netProd + (dataPerHour.getGross() - dataPerHour.getScrap());
            }
            row.setProduction(netProd);
            rows.add(row);
        }

        return rows;
    }

    /**
     *
     * @param from  (required)
     * @param to    (required)
     * @return      (required)
     */
    public List<PercentageResponse> getScrapPercentage(LocalDateTime from, LocalDateTime to) {
        List<ProductionData>  data = getProdData(from, to);

        List<PercentageResponse> rows = new ArrayList<>();
        for(ProductionData element : data) {

            double gross = 0;
            double scrap = 0;
            for(DataPerHour dataPerHour : element.getDataPerHour().values()) {
                gross = gross + dataPerHour.getGross();
                scrap = scrap + dataPerHour.getScrap();
            }

            PercentageResponse row = new PercentageResponse(scrap/gross);
            populateGeneralResponse(element.getMachineName(), from, to, row);
            rows.add(row);
        }

        return rows;
    }

    public List<PercentageResponse> getDowntimePercentage(LocalDateTime from, LocalDateTime to) {
        List<PercentageResponse> rows = new ArrayList<>();
        Collection<RuntimeData> data = getRunData(from, to).values();

        for(RuntimeData element : data) {
            List<LocalTime> runningTimeStamps = element.getRunningTimeStamps();
            List<String> runningTimeValues = element.getRunningTimeValues();
            if(runningTimeStamps.size() != runningTimeValues.size()) {
                // throw exception
            }

            double downtimeMinutes = 0;
            LocalTime lastDownTime = null;
            for(int i=0; i<runningTimeStamps.size(); i++) {
                // 1 is true; 0=false
                boolean isRunning = runningTimeValues.get(i).equals("1");
                if(isRunning) {
                    if(lastDownTime != null) {
                        downtimeMinutes = downtimeMinutes + MINUTES.between(lastDownTime, runningTimeStamps.get(i));
                        lastDownTime = null;
                    }
                } else {
                    if(lastDownTime == null) {
                        lastDownTime = runningTimeStamps.get(i);
                    }
                }
            }

            double minutesIn24hrs = 24*60;
            PercentageResponse row = new PercentageResponse(downtimeMinutes/minutesIn24hrs);
            populateGeneralResponse(element.getMachineName(), from, to, row);

            rows.add(row);
        }

        return rows;
    }

    /**
     * The net production (gross production – scrap) for every hour
     *
     * @param from  (required)
     * @param to    (required)
     * @return
     */
    public List<ProductionPerHourResponse> getProductionByHour(LocalDateTime from, LocalDateTime to) {
        List<ProductionData>  data = getProdData(from, to);

        List<ProductionPerHourResponse> rows = new ArrayList<>();
        for(ProductionData element : data) {
            ProductionPerHourResponse row = new ProductionPerHourResponse();
            populateGeneralResponse(element.getMachineName(), from, to, row);

            Map<String, Integer> map = row.getProductionPerHour();
            for(Map.Entry<Integer, DataPerHour> entry : element.getDataPerHour().entrySet()) {
                String hour = "H" + entry.getKey();
                DataPerHour dataPerHour = entry.getValue();
                int netProduction = dataPerHour.getGross() - entry.getValue().getScrap();

                map.put(hour, netProduction);
            }

            rows.add(row);
        }

        return rows;
    }

    private void populateGeneralResponse(String machine, LocalDateTime from, LocalDateTime to, GenericResponse row) {
        row.setMachine(machine);
        row.setDateTime_From(from);
        row.setDateTime_To(to);
    }

    /**
     *  Retrieve data from the Production DAO layer
     * @param from
     * @param to
     * @return
     */
    public List<ProductionData> getProdData(LocalDateTime from, LocalDateTime to) {
        List<ProductionData> data = null;
        try {
            data = prodDao.getData(from, to);
        } catch (IOException e) {
            // Throw generic exception
        }

        return data;
    }

    /**
     *  Retrieve data from the Runtime DAO layer
     * @param from
     * @param to
     * @return
     */
    public Map<String, RuntimeData> getRunData(LocalDateTime from, LocalDateTime to) {
        Map<String, RuntimeData> data = null;
        try {
            data = runDao.getData(from, to);
        } catch (IOException e) {
            // Throw generic exception
        }

        return data;
    }
}
