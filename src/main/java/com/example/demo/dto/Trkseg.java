package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class Trkseg {

    private List<Trkpt> trkpt = new ArrayList<>();

    public List<Trkpt> getTrkpt() {
        return trkpt;
    }

    public void setTrkpt(List<Trkpt> trkpt) {
        this.trkpt = trkpt;
    }
}
