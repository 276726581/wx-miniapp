package com.jaspercloud.shipkeeper.config;

import com.jaspercloud.mybaits.mapper.support.CommonMapperFactoryBean;
import com.jaspercloud.shipkeeper.dao.mapper.shipkeeper.CommentMapper;
import com.jaspercloud.shipkeeper.dao.mapper.shipkeeper.GoodsMapper;
import com.jaspercloud.shipkeeper.dao.mapper.shipkeeper.ShipMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonMapperConfig {

    @Bean
    public CommonMapperFactoryBean goodsMapper(SqlSessionFactory sqlSessionFactory) {
        CommonMapperFactoryBean commonMapperFactoryBean = new CommonMapperFactoryBean(GoodsMapper.class);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        commonMapperFactoryBean.setSqlSessionTemplate(sqlSessionTemplate);
        return commonMapperFactoryBean;
    }

    @Bean
    public CommonMapperFactoryBean shipMapper(SqlSessionFactory sqlSessionFactory) {
        CommonMapperFactoryBean commonMapperFactoryBean = new CommonMapperFactoryBean(ShipMapper.class);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        commonMapperFactoryBean.setSqlSessionTemplate(sqlSessionTemplate);
        return commonMapperFactoryBean;
    }

    @Bean
    public CommonMapperFactoryBean commentMapper(SqlSessionFactory sqlSessionFactory) {
        CommonMapperFactoryBean commonMapperFactoryBean = new CommonMapperFactoryBean(CommentMapper.class);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        commonMapperFactoryBean.setSqlSessionTemplate(sqlSessionTemplate);
        return commonMapperFactoryBean;
    }
}
