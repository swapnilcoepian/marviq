package com.swapnil.marviq.model;

import java.util.HashMap;
import java.util.Map;

public class ProductionData {
    private String machineName;
    //key-hour, from 0 to 23, value-DataPerHour
    private Map<Integer, DataPerHour> dataPerHour = new HashMap<>();

    public ProductionData(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineName() {
        return machineName;
    }

    public Map<Integer, DataPerHour> getDataPerHour() {
        return dataPerHour;
    }
}
