package com.jaspercloud.shipkeeper.service;

import com.jaspercloud.shipkeeper.dto.NearByListDTO;
import com.jaspercloud.shipkeeper.dto.ShipDTO;
import com.jaspercloud.shipkeeper.entity.Ship;
import com.jaspercloud.shipkeeper.support.geohash.WGS84Point;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(transactionManager = "shipkeeper")
public interface ShipService {

    void saveShip(ShipDTO shipDTO, List<byte[]> list);

    void saveShip(ShipDTO shipDTO);

    void updateShipById(ShipDTO shipDTO);

    void deleteShipById(ShipDTO shipDTO);

    Ship getShipById(Long id);

    List<Ship> getListByPage(long lastId, int count, Ship ship);

    NearByListDTO<Ship> getListByNearby(WGS84Point wgs84Point, int layer, long lastId, int count);
}
