package com.jaspercloud.shipkeeper.dao;

import com.jaspercloud.shipkeeper.entity.Ship;

import java.util.List;

public interface ShipDao {

    void insertShip(Ship ship);

    void updateShipById(Ship ship);

    void deleteShipById(Long id);

    Ship getShipById(Long id);

    List<Ship> getShipListByPageDesc(long lastId, int count, Ship ship);

    List<Ship> getShipListByNearby(List<Long> list, long lastId, int count);
}
