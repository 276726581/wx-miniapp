package com.jaspercloud.shipkeeper.service.impl;

import com.jaspercloud.shipkeeper.dao.ShipDao;
import com.jaspercloud.shipkeeper.dto.NearByListDTO;
import com.jaspercloud.shipkeeper.dto.ShipDTO;
import com.jaspercloud.shipkeeper.entity.Ship;
import com.jaspercloud.shipkeeper.exception.ErrorOperationException;
import com.jaspercloud.shipkeeper.exception.NotFoundException;
import com.jaspercloud.shipkeeper.exception.SaveFileException;
import com.jaspercloud.shipkeeper.service.FileStorageService;
import com.jaspercloud.shipkeeper.service.ShipService;
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
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipDao shipDao;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public void saveShip(ShipDTO shipDTO, List<byte[]> list) {
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
        shipDTO.setImages(fileNameList);
        saveShip(shipDTO);
    }

    @Override
    public void saveShip(ShipDTO shipDTO) {
        WGS84Point wgs84Point = new WGS84Point(shipDTO.getLat(), shipDTO.getLng());
        GeoHash geoHash = GeoHashUtil.encodeGeoHash(wgs84Point);
        Ship ship = new Ship();
        DTOMapUtil.map(shipDTO, ship);
        ship.setLat(wgs84Point.getLatitude());
        ship.setLng(wgs84Point.getLongitude());
        ship.setGeoHashLong(geoHash.longValue());
        ship.setGeoHash5(geoHash.toBase32());
        ship.setCreateTime(new Date());
        shipDao.insertShip(ship);
    }

    @Override
    public void updateShipById(ShipDTO shipDTO) {
        Ship result = shipDao.getShipById(shipDTO.getId());
        if (null == result) {
            throw new NotFoundException();
        }
        if (!result.getUid().equals(shipDTO.getUid())) {
            throw new ErrorOperationException();
        }

        Ship ship = new Ship();
        DTOMapUtil.map(shipDTO, ship);
        shipDao.updateShipById(ship);
    }

    @Override
    public void deleteShipById(ShipDTO shipDTO) {
        Ship result = shipDao.getShipById(shipDTO.getId());
        if (null == result) {
            throw new NotFoundException();
        }
        if (!result.getUid().equals(shipDTO.getUid())) {
            throw new ErrorOperationException();
        }

        shipDao.deleteShipById(shipDTO.getId());
    }

    @Override
    public Ship getShipById(Long id) {
        Ship ship = shipDao.getShipById(id);
        return ship;
    }

    @Override
    public List<Ship> getListByPage(long lastId, int count, Ship ship) {
        List<Ship> list = shipDao.getShipListByPageDesc(lastId, count, ship);
        return list;
    }

    @Override
    public NearByListDTO<Ship> getListByNearby(WGS84Point wgs84Point, int layer, long lastId, int count) {
        List<Ship> resultList = new ArrayList<>();
        List<Ship> tmp;
        do {
            List<Long> geoList = GeoHashUtil.around(wgs84Point, layer);
            tmp = shipDao.getShipListByNearby(geoList, lastId, count);
            resultList.addAll(tmp);
            if (resultList.size() >= count) {
                break;
            }
            if (tmp.isEmpty()) {
                layer++;
                lastId = Long.MAX_VALUE;
            } else {
                lastId = tmp.get(tmp.size() - 1).getId();
            }
        } while (layer < 30 && resultList.size() < count);
        NearByListDTO<Ship> nearByListDTO = new NearByListDTO<>(layer, resultList, Long.MAX_VALUE);
        nearByListDTO.setLat(wgs84Point.getLatitude());
        nearByListDTO.setLng(wgs84Point.getLongitude());
        if (resultList.isEmpty()) {
            nearByListDTO.setLastId(Long.MAX_VALUE);
        } else {
            Ship ship = resultList.get(resultList.size() - 1);
            nearByListDTO.setLastId(ship.getId());
        }

        return nearByListDTO;
    }
}
