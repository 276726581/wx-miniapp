package com.jaspercloud.shipkeeper.service;

import com.jaspercloud.shipkeeper.dto.GoodsDTO;
import com.jaspercloud.shipkeeper.dto.NearByListDTO;
import com.jaspercloud.shipkeeper.entity.Goods;
import com.jaspercloud.shipkeeper.entity.Ship;
import com.jaspercloud.shipkeeper.support.geohash.WGS84Point;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(transactionManager = "shipkeeper")
public interface GoodsService {

    void saveGoods(GoodsDTO goodsDTO, List<byte[]> list);

    void saveGoods(GoodsDTO goodsDTO);

    void updateGoodsById(GoodsDTO goodsDTO);

    void deleteGoodsById(GoodsDTO goodsDTO);

    Goods getGoodsById(Long id);

    List<Goods> getListByPage(long lastId, int count, Goods goods);

    NearByListDTO<Goods> getListByNearby(WGS84Point wgs84Point, int layer, long lastId, int count);
}
