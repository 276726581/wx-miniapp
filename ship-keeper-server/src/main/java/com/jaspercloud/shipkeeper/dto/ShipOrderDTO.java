package com.jaspercloud.shipkeeper.dto;

import com.jaspercloud.shipkeeper.entity.Ship;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShipOrderDTO {

    private Long id;
    private List<Ship> shipList = new ArrayList<>();
    private Date createTime;
    private Integer price;
    private Long sellerId;
    private Long shipId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ship> getShipList() {
        return shipList;
    }

    public void setShipList(List<Ship> shipList) {
        this.shipList = shipList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public void addShip(Ship ship) {
        shipList.add(ship);
    }

    public ShipOrderDTO() {
    }
}
