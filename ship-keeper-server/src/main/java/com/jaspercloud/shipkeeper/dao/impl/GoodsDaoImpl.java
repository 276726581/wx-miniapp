package com.jaspercloud.shipkeeper.dao.impl;

import com.jaspercloud.mybaits.mapper.definition.CommonMapper;
import com.jaspercloud.shipkeeper.dao.GoodsDao;
import com.jaspercloud.shipkeeper.dao.mapper.shipkeeper.GoodsMapper;
import com.jaspercloud.shipkeeper.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoodsDaoImpl implements GoodsDao {

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void insertGoods(Goods goods) {
        commonMapper.insert(Goods.class, goods);
    }

    @Override
    public void updateGoodsById(Goods goods) {
        commonMapper.updateNotNullById(Goods.class, goods);
    }

    @Override
    public void deleteGoodsById(Long id) {
        commonMapper.deleteById(Goods.class, id);
    }

    @Override
    public Goods getGoodsById(Long id) {
        Goods goods = commonMapper.selectOneById(Goods.class, id);
        return goods;
    }

    @Override
    public List<Goods> getGoodsListByPageDesc(long lastId, int count, Goods goods) {
        List<Goods> list = goodsMapper.selectGoodsListByPageDesc(Goods.class, lastId, count, goods);
        return list;
    }

    @Override
    public List<Goods> getShipListByNearby(List<Long> geoList, long lastId, int count) {
        List<Goods> list = goodsMapper.getShipListByNearby(Goods.class, geoList, lastId, count);
        return list;
    }
}
