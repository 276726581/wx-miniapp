package com.jaspercloud.shipkeeper.service.impl;

import com.jaspercloud.shipkeeper.dao.GoodsDao;
import com.jaspercloud.shipkeeper.dto.GoodsDTO;
import com.jaspercloud.shipkeeper.dto.NearByListDTO;
import com.jaspercloud.shipkeeper.entity.Goods;
import com.jaspercloud.shipkeeper.entity.Ship;
import com.jaspercloud.shipkeeper.exception.ErrorOperationException;
import com.jaspercloud.shipkeeper.exception.NotFoundException;
import com.jaspercloud.shipkeeper.exception.SaveFileException;
import com.jaspercloud.shipkeeper.service.FileStorageService;
import com.jaspercloud.shipkeeper.service.GoodsService;
import com.jaspercloud.shipkeeper.support.geohash.GeoHash;
import com.jaspercloud.shipkeeper.support.geohash.GeoHashUtil;
import com.jaspercloud.shipkeeper.support.geohash.WGS84Point;
import com.jaspercloud.shipkeeper.util.DTOMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public void saveGoods(GoodsDTO goodsDTO, List<byte[]> list) {
        List<String> fileNameList = list.stream().map(new Function<byte[], String>() {
            @Override
            public String apply(byte[] bytes) {
                try {
                    File file = fileStorageService.saveFile(bytes, "jpg");
                    return file.getName();
                } catch (Exception e) {
                    throw new SaveFileException(e.getMessage(), e);
                }
            }
        }).collect(Collectors.toList());
        goodsDTO.setImages(fileNameList);
        saveGoods(goodsDTO);
    }

    @Override
    public void saveGoods(GoodsDTO goodsDTO) {
        WGS84Point wgs84Point = new WGS84Point(goodsDTO.getLat(), goodsDTO.getLng());
        GeoHash geoHash = GeoHashUtil.encodeGeoHash(wgs84Point);
        Goods goods = new Goods();
        DTOMapUtil.map(goodsDTO, goods);
        goods.setLat(wgs84Point.getLatitude());
        goods.setLng(wgs84Point.getLongitude());
        goods.setGeoHashLong(geoHash.longValue());
        goods.setGeoHash5(geoHash.toBase32());
        goods.setCreateTime(new Date());
        goodsDao.insertGoods(goods);
    }

    @Override
    public void updateGoodsById(GoodsDTO goodsDTO) {
        Goods result = goodsDao.getGoodsById(goodsDTO.getId());
        if (null == result) {
            throw new NotFoundException();
        }
        if (!result.getUid().equals(goodsDTO.getUid())) {
            throw new ErrorOperationException();
        }

        Goods goods = new Goods();
        DTOMapUtil.map(goodsDTO, goods);
        goodsDao.updateGoodsById(goods);
    }

    @Override
    public void deleteGoodsById(GoodsDTO goodsDTO) {
        Goods result = goodsDao.getGoodsById(goodsDTO.getId());
        if (null == result) {
            throw new NotFoundException();
        }
        if (!result.getUid().equals(goodsDTO.getUid())) {
            throw new ErrorOperationException();
        }

        goodsDao.deleteGoodsById(goodsDTO.getId());
    }

    @Override
    public Goods getGoodsById(Long id) {
        Goods goods = goodsDao.getGoodsById(id);
        return goods;
    }

    @Override
    public List<Goods> getListByPage(long lastId, int count, Goods goods) {
        List<Goods> list = goodsDao.getGoodsListByPageDesc(lastId, count, goods);
        return list;
    }

    @Override
    public NearByListDTO<Goods> getListByNearby(WGS84Point wgs84Point, int layer, long lastId, int count) {
        List<Goods> resultList = new ArrayList<>();
        List<Goods> tmp;
        int i = 0;
        do {
            List<Long> geoList = GeoHashUtil.around(wgs84Point, layer + i);
            tmp = goodsDao.getShipListByNearby(geoList, lastId, count);
            resultList.addAll(tmp);
            if (resultList.size() >= count) {
                break;
            }
            if (tmp.isEmpty()) {
                i++;
                lastId = Long.MAX_VALUE;
            }
        } while (tmp.isEmpty() && resultList.size() < count && i < 30);

        NearByListDTO<Goods> nearByListDTO = new NearByListDTO<>(layer + i, resultList, Long.MAX_VALUE);
        if (resultList.isEmpty()) {
            nearByListDTO.setLastId(Long.MAX_VALUE);
        } else {
            Goods goods = resultList.get(resultList.size() - 1);
            nearByListDTO.setLastId(goods.getId());
        }

        return nearByListDTO;
    }
}
