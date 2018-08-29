package com.jaspercloud.shipkeeper.dao;

import com.jaspercloud.shipkeeper.entity.Goods;

import java.util.List;

public interface GoodsDao {

    void insertGoods(Goods goods);

    void updateGoodsById(Goods goods);

    void deleteGoodsById(Long id);

    Goods getGoodsById(Long id);

    List<Goods> getGoodsListByPageDesc(long lastId, int count, Goods goods);

    List<Goods> getShipListByNearby(List<Long> geoList, long lastId, int count);
}
