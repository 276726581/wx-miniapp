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
@Entity(name = "goods_order")
public class GoodsOrder {

    @GeneratedValue(generator = "select nextval('seq_goods_order')")
    @Id
    private Long id;
    @Column(name = "goods_ids")
    private List<Long> goodsIdList = new ArrayList<>();
    @Column(name = "create_time")
    private Date createTime;
    private Long price;
    @Column(name = "provider_id")
    private Long providerId;
    @Column(name = "buyer_id")
    private Long buyerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getGoodsIdList() {
        return goodsIdList;
    }

    public void setGoodsIdList(List<Long> goodsIdList) {
        this.goodsIdList = goodsIdList;
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

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void addGoodsId(Long id) {
        goodsIdList.add(id);
    }

    public GoodsOrder() {
    }
}
