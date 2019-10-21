package com.swapnil.marviq.model;

import java.util.ArrayList;
import java.util.List;

public class DataPerHour {
    private int gross;
    private int scrap;
    private List<Integer> temp = new ArrayList<>();

    public int getGross() {
        return gross;
    }

    public void addGross(int gross) {
        this.gross = this.gross + gross;
    }

    public int getScrap() {
        return scrap;
    }

    public void addScrap(int scrap) {
        this.scrap = this.scrap + scrap;
    }

    public List<Integer> getTemp() {
        return temp;
    }

    public void addTemp(Integer temp) {
        this.temp.add(temp);
    }
}
