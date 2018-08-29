package com.jaspercloud.shipkeeper.dto;

import java.util.List;

public class NearByListDTO<T> {

    private double lat;
    private double lng;
    private int layer = 1;
    private long lastId = Long.MAX_VALUE;
    private List<T> data;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getLastId() {
        return lastId;
    }

    public void setLastId(long lastId) {
        this.lastId = lastId;
    }

    public NearByListDTO(int layer, List<T> data, long lastId) {
        this.layer = layer;
        this.data = data;
        this.lastId = lastId;
    }
}
