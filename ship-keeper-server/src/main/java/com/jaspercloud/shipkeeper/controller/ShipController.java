package com.jaspercloud.shipkeeper.controller;

import com.jaspercloud.shipkeeper.dto.NearByListDTO;
import com.jaspercloud.shipkeeper.dto.ShipDTO;
import com.jaspercloud.shipkeeper.dto.UserDTO;
import com.jaspercloud.shipkeeper.entity.Ship;
import com.jaspercloud.shipkeeper.service.ShipService;
import com.jaspercloud.shipkeeper.service.UserService;
import com.jaspercloud.shipkeeper.support.geohash.WGS84Point;
import com.jaspercloud.shipkeeper.util.DTOMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ship")
public class ShipController {

    @Autowired
    private ShipService shipService;

    @Autowired
    private UserService userService;

    @PostMapping
    public void save(@RequestBody ShipDTO shipDTO) {
        shipService.saveShip(shipDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGoods(@PathVariable("id") Long id, @RequestBody ShipDTO shipDTO) {
        shipDTO.setId(id);
        shipService.updateShipById(shipDTO);
        ResponseEntity<Void> entity = ResponseEntity.ok().build();
        return entity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") Long id, @RequestParam("uid") String uid) {
        ShipDTO shipDTO = new ShipDTO();
        shipDTO.setId(id);
        shipDTO.setUid(uid);
        shipService.deleteShipById(shipDTO);
        ResponseEntity<Void> entity = ResponseEntity.ok().build();
        return entity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipDTO> getById(@PathVariable("id") Long id) {
        Ship ship = shipService.getShipById(id);
        ShipDTO shipDTO = new ShipDTO();
        DTOMapUtil.map(ship, shipDTO);
        UserDTO userInfo = userService.getUserInfo(ship.getUid());
        shipDTO.setAvatarUrl(userInfo.getAvatarUrl());
        shipDTO.setNickName(userInfo.getNickName());
        ResponseEntity<ShipDTO> entity = ResponseEntity.ok().body(shipDTO);
        return entity;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ShipDTO>> getList(@RequestParam(value = "lastId", defaultValue = "-1", required = false) Long lastId,
                                                 @RequestParam("count") Integer count,
                                                 @ModelAttribute Ship ship) {
        if (lastId < 0) {
            lastId = Long.MAX_VALUE;
        }
        List<Ship> list = shipService.getListByPage(lastId, count, ship);
        List<ShipDTO> result = list.parallelStream().map(new Function<Ship, ShipDTO>() {
            @Override
            public ShipDTO apply(Ship ship) {
                ShipDTO shipDTO = new ShipDTO();
                DTOMapUtil.map(ship, shipDTO);
                UserDTO userInfo = userService.getUserInfo(ship.getUid());
                shipDTO.setAvatarUrl(userInfo.getAvatarUrl());
                shipDTO.setNickName(userInfo.getNickName());
                return shipDTO;
            }
        }).collect(Collectors.toList());
        ResponseEntity<List<ShipDTO>> entity = ResponseEntity.ok().body(result);
        return entity;
    }

    @GetMapping("/geo/list")
    public ResponseEntity<NearByListDTO> getGeoList(@ModelAttribute NearByListDTO nearByListDTO, @RequestParam("count") Integer count) {
        if (nearByListDTO.getLastId() < 0) {
            nearByListDTO.setLastId(Long.MAX_VALUE);
        }
        WGS84Point wgs84Point = new WGS84Point(nearByListDTO.getLat(), nearByListDTO.getLng());
        NearByListDTO dto = shipService.getListByNearby(wgs84Point, nearByListDTO.getLayer(), nearByListDTO.getLastId(), count);
        List<Ship> list = dto.getData();
        List<ShipDTO> result = list.parallelStream().map(new Function<Ship, ShipDTO>() {
            @Override
            public ShipDTO apply(Ship ship) {
                ShipDTO shipDTO = new ShipDTO();
                DTOMapUtil.map(ship, shipDTO);
                UserDTO userInfo = userService.getUserInfo(ship.getUid());
                shipDTO.setAvatarUrl(userInfo.getAvatarUrl());
                shipDTO.setNickName(userInfo.getNickName());
                return shipDTO;
            }
        }).collect(Collectors.toList());
        dto.setData(result);
        ResponseEntity<NearByListDTO> entity = ResponseEntity.ok().body(dto);
        return entity;
    }
}
