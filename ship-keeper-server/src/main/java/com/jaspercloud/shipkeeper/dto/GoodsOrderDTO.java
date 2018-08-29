package com.jaspercloud.shipkeeper.dto;

import com.jaspercloud.shipkeeper.entity.Goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoodsOrderDTO {

    private Long id;
    private List<Goods> goodsList = new ArrayList<>();
    private Date createTime;
    private Integer price;
    private Long providerId;
    private Long buyerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
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

    public void addGoods(Goods goods) {
        goodsList.add(goods);
    }

    public GoodsOrderDTO() {
    }
}
