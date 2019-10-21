package com.swapnil.marviq.model.response;

/**
 * UI Response model to display net production of the machine in the given duration
 */
public class NetProductionResponse extends GenericResponse {

    private int production;

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }
}
