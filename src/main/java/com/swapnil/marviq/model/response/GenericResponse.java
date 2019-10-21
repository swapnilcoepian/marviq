package com.swapnil.marviq.model.response;

import java.time.LocalDateTime;

/**
 * Generic response model
 */
public class GenericResponse {

    private String machine;
    private LocalDateTime dateTime_From;
    private LocalDateTime dateTime_To;

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public LocalDateTime getDateTime_From() {
        return dateTime_From;
    }

    public void setDateTime_From(LocalDateTime dateTime_From) {
        this.dateTime_From = dateTime_From;
    }

    public LocalDateTime getDateTime_To() {
        return dateTime_To;
    }

    public void setDateTime_To(LocalDateTime dateTime_To) {
        this.dateTime_To = dateTime_To;
    }
}
