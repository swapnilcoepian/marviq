package com.swapnil.marviq.dao;

import com.swapnil.marviq.model.RuntimeData;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 *  Dao layer to fetch machine runtime data
 * TODO: Ideally, DAO class shouldn't deal with any data manipulation. Move such logic to a different class.
 */
@Repository
public class RuntimeDao {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String FILE_NAME = "/Users/Swapnil/Downloads/Marviq/Runtime.csv";

    public Map<String, RuntimeData> getData(LocalDateTime from, LocalDateTime to) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(FILE_NAME));
        // key-machineName, value-RunTimeData
        Map<String, RuntimeData> map = new HashMap<>();

        while (csvReader.readLine() != null) {
            String[] row = csvReader.readLine().split(",");
            LocalDateTime timeStamp = LocalDateTime.parse(row[2].replaceAll("\"", ""), FORMATTER);

            if (timeStamp.isBefore(from)) {
                // Do nothing
                continue;
            } else if (timeStamp.isBefore(to)) {
                String machineName = row[1].replaceAll("\"", "");
                RuntimeData runtimeData = map.get(machineName);

                if (runtimeData == null) {
                    runtimeData = new RuntimeData(machineName);
                    map.put(machineName, runtimeData);
                }

                runtimeData.addRunningTimeStamps(timeStamp.toLocalTime());
                runtimeData.addRunningTimeValue(row[3]);
            } else if (timeStamp.isAfter(to)) {
                // Terminate the loop as data is sorted chronologically
                break;
            }
        }

        csvReader.close();
        return map;
    }
}
