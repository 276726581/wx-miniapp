package com.jaspercloud.shipkeeper.entity;

import com.jaspercloud.mybaits.mapper.annotation.FieldTypeDiscriminator;
import com.jaspercloud.shipkeeper.support.mybatis.ImageListTypeHandler;
import com.jaspercloud.shipkeeper.support.mybatis.MapTypeHandler;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.*;

//@SubTable
@Entity(name = "goods")
public class Goods {

    @GeneratedValue(generator = "select nextval('seq_goods')")
    @Id
    private Long id;
    private String uid;
    private String type;
    private Integer price;
    private String unit;
    private String province;
    private String city;
    private String content;
    @Column(name = "create_time")
    private Date createTime;
    @FieldTypeDiscriminator(typeHandler = MapTypeHandler.class)
    @Column(name = "specification")
    private Map<String, String> specificationMap = new HashMap<>();
    @FieldTypeDiscriminator(typeHandler = ImageListTypeHandler.class)
    @Column(name = "images")
    private List<String> imageList = new ArrayList<>();
    private String phone;
    private String wechat;
    private String address;
    private Double lat;
    private Double lng;
    @Column(name = "geohash_long")
    private Long geoHashLong;
    @Column(name = "geohash5")
    private String geoHash5;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Map<String, String> getSpecificationMap() {
        return specificationMap;
    }

    public void setSpecificationMap(Map<String, String> specificationMap) {
        this.specificationMap = specificationMap;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addSpecification(String key, String value) {
        specificationMap.put(key, value);
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Long getGeoHashLong() {
        return geoHashLong;
    }

    public void setGeoHashLong(Long geoHashLong) {
        this.geoHashLong = geoHashLong;
    }

    public String getGeoHash5() {
        return geoHash5;
    }

    public void setGeoHash5(String geoHash5) {
        this.geoHash5 = geoHash5;
    }

    public void addImage(String image) {
        imageList.add(image);
    }
}
