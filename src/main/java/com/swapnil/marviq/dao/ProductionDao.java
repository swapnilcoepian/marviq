package com.swapnil.marviq.dao;

import com.swapnil.marviq.model.DataPerHour;
import com.swapnil.marviq.model.ProductionData;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao layer to fetch machine production data
 * TODO: Ideally, DAO class shouldn't deal with any data manipulation. Move such logic to a different class.
 */
@Repository
public class ProductionDao {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String FILE_NAME = "/Users/Swapnil/Downloads/Marviq/Production.csv";

    public List<ProductionData> getData(LocalDateTime from, LocalDateTime to) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(FILE_NAME));
        // key-machineName, value-ProductionData
        Map<String, ProductionData> map = new HashMap<>();

        while (csvReader.readLine() != null) {
            String[] data = csvReader.readLine().split(",");

            // extract parameter values
            String machineName = data[1].replaceAll("\"", "");
            String variable = data[2].replaceAll("\"", "");
            LocalDateTime fromTimeStamp = LocalDateTime.parse(data[3].replaceAll("\"", ""), FORMATTER);
            int value = Integer.parseInt(data[5]);

            // check for duration
            if (fromTimeStamp.isBefore(from)) {
                // Do nothing
                continue;
            } else if (fromTimeStamp.isBefore(to)) {
                ProductionData prodData = map.get(machineName);

                if (prodData == null) {
                    prodData = new ProductionData(machineName);
                    map.put(machineName, prodData);
                }

                // Populate dataPerHour
                int hour = fromTimeStamp.getHour();
                Map<Integer, DataPerHour> dataPerHourMap = prodData.getDataPerHour();
                DataPerHour dataPerHour = dataPerHourMap.get(hour);
                if (dataPerHour == null) {
                    dataPerHour = new DataPerHour();
                    dataPerHourMap.put(hour, dataPerHour);
                }

                setVariable(variable, value, dataPerHour);

            } else if (fromTimeStamp.isAfter(to)) {
                // Terminate the loop as data is sorted chronologically
                break;
            }
        }

        csvReader.close();
        return new ArrayList<>(map.values());
    }

    /**
     * TODO: Create enum for the switch case
     *
     * @param variable  (required)
     * @param value     (required)
     * @param dataPerHour (required)
     */
    private void setVariable(String variable, int value, DataPerHour dataPerHour) {
        switch(variable) {
            case "PRODUCTION":
                dataPerHour.addGross(value);
                break;
            case "SCRAP":
                dataPerHour.addScrap(value);
                break;
            case "CORE TEMPERATURE":
                dataPerHour.addTemp(value);
                break;
            default:
                // throw exception
        }
    }
}
