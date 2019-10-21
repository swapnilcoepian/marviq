package com.swapnil.marviq.model.response;

/**
 * Response model to display the percentage of scrap for the machine
 */
public class PercentageResponse extends GenericResponse {
    private double percentage;

    public PercentageResponse(double percentage) {
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }
}
