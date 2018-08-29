package com.jaspercloud.shipkeeper.dto;

import com.jaspercloud.shipkeeper.support.geohash.WGS84Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShipDTO {

    private Long id;
    private String uid;
    private String avatarUrl;
    private String nickName;
    private Integer price;
    private String unit;
    private String province;
    private String city;
    private String ais;
    private String content;
    private String date;
    private List<String> images = new ArrayList<>();
    private String phone;
    private String wechat;
    private String location;
    private WGS84Point wgs84Point;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAis() {
        return ais;
    }

    public void setAis(String ais) {
        this.ais = ais;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public WGS84Point getWgs84Point() {
        return wgs84Point;
    }

    public void setWgs84Point(WGS84Point wgs84Point) {
        this.wgs84Point = wgs84Point;
    }

    public ShipDTO() {
    }
}