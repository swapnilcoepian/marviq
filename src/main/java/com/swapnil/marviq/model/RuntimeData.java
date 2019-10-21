package com.swapnil.marviq.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RuntimeData {

    private String machineName;
    private List<LocalTime> runningTimeStamps = new ArrayList<>();
    private List<String> runningTimeValues = new ArrayList<>();

    public RuntimeData(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineName() {
        return machineName;
    }

    public List<LocalTime> getRunningTimeStamps() {
        return runningTimeStamps;
    }

    public void addRunningTimeStamps(LocalTime downTimeStamp) {
        this.runningTimeStamps.add(downTimeStamp);
    }

    public List<String> getRunningTimeValues() {
        return runningTimeValues;
    }

    public void addRunningTimeValue(String runningTimeValue) {
        this.runningTimeValues.add(runningTimeValue);
    }
}
