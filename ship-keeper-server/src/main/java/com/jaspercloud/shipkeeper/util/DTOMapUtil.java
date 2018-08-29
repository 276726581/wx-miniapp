package com.jaspercloud.shipkeeper.util;

import com.jaspercloud.shipkeeper.dto.GoodsDTO;
import com.jaspercloud.shipkeeper.dto.ShipDTO;
import com.jaspercloud.shipkeeper.entity.Goods;
import com.jaspercloud.shipkeeper.entity.Ship;
import com.jaspercloud.shipkeeper.support.geohash.WGS84Point;
import org.apache.commons.lang3.time.DateFormatUtils;

public final class DTOMapUtil {

    private DTOMapUtil() {

    }

    public static void map(Ship left, ShipDTO right) {
        right.setId(left.getId());
        right.setUid(left.getUid());
        right.setPrice(left.getPrice());
        right.setUnit(left.getUnit());
        right.setProvince(left.getProvince());
        right.setCity(left.getCity());
        right.setAis(left.getAis());
        right.setContent(left.getContent());
        right.setDate(DateFormatUtils.format(left.getCreateTime(), "yyyy-MM-dd"));
        right.setImages(left.getImageList());
        right.setPhone(left.getPhone());
        right.setWechat(left.getWechat());
        right.setLocation(left.getLocation());
        if (null != left.getLat() && null != left.getLng()) {
            WGS84Point wgs84Point = new WGS84Point(left.getLat(), left.getLng());
            right.setWgs84Point(wgs84Point);
        }
    }

    public static void map(ShipDTO left, Ship right) {
        right.setId(left.getId());
        right.setUid(left.getUid());
        right.setPrice(left.getPrice());
        right.setUnit(left.getUnit());
        right.setProvince(left.getProvince());
        right.setCity(left.getCity());
        right.setAis(left.getAis());
        right.setContent(left.getContent());
        right.setImageList(left.getImages());
        right.setPhone(left.getPhone());
        right.setWechat(left.getWechat());
        right.setLocation(left.getLocation());
        if (null != left.getWgs84Point()) {
            right.setLat(left.getWgs84Point().getLatitude());
            right.setLng(left.getWgs84Point().getLongitude());
        }
    }

    public static void map(GoodsDTO left, Goods right) {
        right.setId(left.getId());
        right.setUid(left.getUid());
        right.setType(left.getGoodsType());
        right.setPrice(left.getPrice());
        right.setUnit(left.getUnit());
        right.setProvince(left.getProvince());
        right.setCity(left.getCity());
        right.setSpecificationMap(left.getSpecificationMap());
        right.setImageList(left.getImages());
        right.setPhone(left.getPhone());
        right.setContent(left.getContent());
        right.setWechat(left.getWechat());
        right.setLocation(left.getLocation());
        if (null != left.getWgs84Point()) {
            right.setLat(left.getWgs84Point().getLatitude());
            right.setLng(left.getWgs84Point().getLongitude());
        }
    }

    public static void map(Goods left, GoodsDTO right) {
        right.setId(left.getId());
        right.setUid(left.getUid());
        right.setGoodsType(left.getType());
        right.setPrice(left.getPrice());
        right.setUnit(left.getUnit());
        right.setProvince(left.getProvince());
        right.setCity(left.getCity());
        right.setDate(DateFormatUtils.format(left.getCreateTime(), "yyyy-MM-dd"));
        right.setSpecificationMap(left.getSpecificationMap());
        right.setImages(left.getImageList());
        right.setPhone(left.getPhone());
        right.setContent(left.getContent());
        right.setWechat(left.getWechat());
        right.setLocation(left.getLocation());
        if (null != left.getLat() && null != left.getLng()) {
            WGS84Point wgs84Point = new WGS84Point(left.getLat(), left.getLng());
            right.setWgs84Point(wgs84Point);
        }
    }
}
