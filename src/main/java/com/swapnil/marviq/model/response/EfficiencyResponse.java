package com.swapnil.marviq.model.response;

public class EfficiencyResponse extends GenericResponse {

    private double availability;
    private double quality;
    private double performance;
    private double oee;

    public double getAvailability() {
        return availability;
    }

    public void setAvailability(double availability) {
        this.availability = availability;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public double getPerformance() {
        return performance;
    }

    public void setPerformance(double performance) {
        this.performance = performance;
    }

    public void calculateOee() {
        oee = availability * quality * performance;
    }
}
