package com.jaspercloud.shipkeeper.dao.mapper.shipkeeper;

import com.jaspercloud.mybaits.mapper.definition.CommonMapper;
import com.jaspercloud.shipkeeper.entity.Ship;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.util.StringUtils;

import java.util.List;

public interface ShipMapper {

    @SelectProvider(type = Provider.class, method = "selectShipListByPageDesc")
    List<Ship> selectShipListByPageDesc(Class<Ship> clazz,
                                        @Param("lastId") Long lastId,
                                        @Param("count") Integer count,
                                        @Param(CommonMapper.PARAM) Ship ship);

    @SelectProvider(type = Provider.class, method = "getShipListByNearby")
    List<Ship> getShipListByNearby(Class<Ship> clazz, List<Long> geoList, @Param("lastId") long lastId, @Param("count") int count);

    class Provider {

        public String selectShipListByPageDesc(Class<Ship> clazz,
                                               @Param("lastId") Long lastId,
                                               @Param("count") Integer count,
                                               @Param(CommonMapper.PARAM) Ship ship) {
            StringBuilder builder = new StringBuilder();
            builder.append("select * from ship").append(" where id < #{lastId}");
            if (!StringUtils.isEmpty(ship.getProvince())) {
                builder.append(" and");
                builder.append(" province = #{param.province}");
            }
            if (!StringUtils.isEmpty(ship.getCity())) {
                builder.append(" and");
                builder.append(" city = #{param.city}");
            }
            builder.append(" order by id desc").append(" limit").append(" ").append(count);
            String sql = builder.toString();
            return sql;
        }

        public String getShipListByNearby(Class<Ship> clazz,
                                          List<Long> geoList,
                                          @Param("lastId") long lastId,
                                          @Param("count") int count) {
            StringBuilder builder = new StringBuilder();
            builder.append("select * from ship").append(" where geohash_long in ");
            builder.append("(");
            for (Long geohash : geoList) {
                builder.append(geohash).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(")");
            builder.append(" and id < #{lastId}");
            builder.append(" order by id desc").append(" limit").append(" ").append(count);
            String sql = builder.toString();
            return sql;
        }
    }
}
