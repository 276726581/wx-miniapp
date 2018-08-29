package com.jaspercloud.shipkeeper.entity;


import com.jaspercloud.mybaits.mapper.annotation.SubTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SubTable
@Entity(name = "ship_order")
public class ShipOrder {

    @GeneratedValue(generator = "select nextval('seq_ship_order')")
    @Id
    private Long id;
    @Column(name = "ship_ids")
    private List<Long> shipIdList = new ArrayList<>();
    @Column(name = "create_time")
    private Date createTime;
    private Long price;
    @Column(name = "seller_id")
    private Long sellerId;
    @Column(name = "ship_id")
    private Long shipId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getShipIdList() {
        return shipIdList;
    }

    public void setShipIdList(List<Long> shipIdList) {
        this.shipIdList = shipIdList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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

    public void addShipId(Long id) {
        shipIdList.add(id);
    }

    public ShipOrder() {
    }
}
