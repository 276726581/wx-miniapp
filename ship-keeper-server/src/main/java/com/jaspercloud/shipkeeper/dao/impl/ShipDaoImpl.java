package com.jaspercloud.shipkeeper.dao.impl;

import com.jaspercloud.mybaits.mapper.definition.CommonMapper;
import com.jaspercloud.shipkeeper.dao.ShipDao;
import com.jaspercloud.shipkeeper.dao.mapper.shipkeeper.ShipMapper;
import com.jaspercloud.shipkeeper.entity.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShipDaoImpl implements ShipDao {

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private ShipMapper shipMapper;

    @Override
    public void insertShip(Ship ship) {
        commonMapper.insert(Ship.class, ship);
    }

    @Override
    public void updateShipById(Ship ship) {
        commonMapper.updateNotNullById(Ship.class, ship);
    }

    @Override
    public void deleteShipById(Long id) {
        commonMapper.deleteById(Ship.class, id);
    }

    @Override
    public Ship getShipById(Long id) {
        Ship ship = commonMapper.selectOneById(Ship.class, id);
        return ship;
    }

    @Override
    public List<Ship> getShipListByPageDesc(long lastId, int count, Ship ship) {
        List<Ship> list = shipMapper.selectShipListByPageDesc(Ship.class, lastId, count, ship);
        return list;
    }

    @Override
    public List<Ship> getShipListByNearby(List<Long> geoList, long lastId, int count) {
        List<Ship> list = shipMapper.getShipListByNearby(Ship.class, geoList, lastId, count);
        return list;
    }
}
