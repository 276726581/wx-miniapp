package com.jaspercloud.shipkeeper.controller;

import com.jaspercloud.shipkeeper.dto.GoodsDTO;
import com.jaspercloud.shipkeeper.dto.NearByListDTO;
import com.jaspercloud.shipkeeper.dto.UserDTO;
import com.jaspercloud.shipkeeper.entity.Goods;
import com.jaspercloud.shipkeeper.service.GoodsService;
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
@RequestMapping("/api/v1/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @PostMapping
    public void save(@RequestBody GoodsDTO goodsDTO) {
        goodsService.saveGoods(goodsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGoods(@PathVariable("id") Long id, @RequestBody GoodsDTO goodsDTO) {
        goodsDTO.setId(id);
        goodsService.updateGoodsById(goodsDTO);
        ResponseEntity<Void> entity = ResponseEntity.ok().build();
        return entity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") Long id, @RequestParam("uid") String uid) {
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setId(id);
        goodsDTO.setUid(uid);
        goodsService.deleteGoodsById(goodsDTO);
        ResponseEntity<Void> entity = ResponseEntity.ok().build();
        return entity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoodsDTO> getDetail(@PathVariable("id") Long id) {
        Goods goods = goodsService.getGoodsById(id);
        GoodsDTO goodsDTO = new GoodsDTO();
        DTOMapUtil.map(goods, goodsDTO);
        UserDTO userInfo = userService.getUserInfo(goods.getUid());
        goodsDTO.setAvatarUrl(userInfo.getAvatarUrl());
        goodsDTO.setNickName(userInfo.getNickName());
        ResponseEntity<GoodsDTO> entity = ResponseEntity.ok().body(goodsDTO);
        return entity;
    }

    @GetMapping("/list")
    public ResponseEntity<List<GoodsDTO>> getList(@RequestParam(value = "lastId", defaultValue = "-1", required = false) Long lastId,
                                                  @RequestParam("count") Integer count,
                                                  @ModelAttribute Goods goods) {
        if (lastId < 0) {
            lastId = Long.MAX_VALUE;
        }
        List<Goods> list = goodsService.getListByPage(lastId, count, goods);
        List<GoodsDTO> result = list.parallelStream().map(new Function<Goods, GoodsDTO>() {
            @Override
            public GoodsDTO apply(Goods goods) {
                GoodsDTO goodsDTO = new GoodsDTO();
                DTOMapUtil.map(goods, goodsDTO);
                UserDTO userInfo = userService.getUserInfo(goods.getUid());
                goodsDTO.setAvatarUrl(userInfo.getAvatarUrl());
                goodsDTO.setNickName(userInfo.getNickName());
                return goodsDTO;
            }
        }).collect(Collectors.toList());
        ResponseEntity<List<GoodsDTO>> entity = ResponseEntity.ok().body(result);
        return entity;
    }

    @GetMapping("/geo/list")
    public ResponseEntity<NearByListDTO> getGeoList(@ModelAttribute NearByListDTO nearByListDTO, @RequestParam("count") Integer count) {
        if (nearByListDTO.getLastId() < 0) {
            nearByListDTO.setLastId(Long.MAX_VALUE);
        }
        WGS84Point wgs84Point = new WGS84Point(nearByListDTO.getLat(), nearByListDTO.getLng());
        NearByListDTO dto = goodsService.getListByNearby(wgs84Point, nearByListDTO.getLayer(), nearByListDTO.getLastId(), count);
        List<Goods> list = dto.getData();
        List<GoodsDTO> result = list.parallelStream().map(new Function<Goods, GoodsDTO>() {
            @Override
            public GoodsDTO apply(Goods goods) {
                GoodsDTO goodsDTO = new GoodsDTO();
                DTOMapUtil.map(goods, goodsDTO);
                UserDTO userInfo = userService.getUserInfo(goods.getUid());
                goodsDTO.setAvatarUrl(userInfo.getAvatarUrl());
                goodsDTO.setNickName(userInfo.getNickName());
                return goodsDTO;
            }
        }).collect(Collectors.toList());
        dto.setData(result);
        ResponseEntity<NearByListDTO> entity = ResponseEntity.ok().body(dto);
        return entity;
    }
}
