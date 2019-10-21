package com.swapnil.marviq.model.response;

import java.util.Map;
import java.util.TreeMap;

public class ProductionPerHourResponse extends GenericResponse {
    // key - hour, value - net production value
    // Treemap data structure is used to maintain the hour sequence
    private Map<String, Integer> productionPerHour = new TreeMap<>();

    public Map<String, Integer> getProductionPerHour() {
        return productionPerHour;
    }

    public void setProductionPerHour(Map<String, Integer> productionPerHour) {
        this.productionPerHour = productionPerHour;
    }
}
