package com.jaspercloud.shipkeeper.dao.mapper.shipkeeper;

import com.jaspercloud.mybaits.mapper.definition.CommonMapper;
import com.jaspercloud.shipkeeper.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.util.StringUtils;

import java.util.List;

public interface GoodsMapper {

    @SelectProvider(type = Provider.class, method = "selectGoodsListByPageDesc")
    List<Goods> selectGoodsListByPageDesc(Class<Goods> clazz,
                                          @Param("lastId") Long lastId,
                                          @Param("count") Integer count,
                                          @Param(CommonMapper.PARAM) Goods goods);

    @SelectProvider(type = Provider.class, method = "getShipListByNearby")
    List<Goods> getShipListByNearby(Class<Goods> goodsClass, List<Long> geoList, @Param("lastId") long lastId, @Param("count") int count);

    class Provider {

        public String selectGoodsListByPageDesc(Class<Goods> clazz,
                                                @Param("lastId") Long lastId,
                                                @Param("count") Integer count,
                                                @Param(CommonMapper.PARAM) Goods goods) {
            StringBuilder builder = new StringBuilder();
            builder.append("select * from goods").append(" where id < #{lastId}");
            if (!StringUtils.isEmpty(goods.getProvince())) {
                builder.append(" and");
                builder.append(" province = #{param.province}");
            }
            if (!StringUtils.isEmpty(goods.getCity())) {
                builder.append(" and");
                builder.append(" city = #{param.city}");
            }
            if (!StringUtils.isEmpty(goods.getType())) {
                builder.append(" and");
                builder.append(" type = #{param.type}");
            }
            builder.append(" order by id desc").append(" limit").append(" ").append(count);
            String sql = builder.toString();
            return sql;
        }

        public String getShipListByNearby(Class<Goods> clazz,
                                          List<Long> geoList,
                                          @Param("lastId") long lastId,
                                          @Param("count") int count) {
            StringBuilder builder = new StringBuilder();
            builder.append("select * from goods").append(" where geohash_long in ");
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
