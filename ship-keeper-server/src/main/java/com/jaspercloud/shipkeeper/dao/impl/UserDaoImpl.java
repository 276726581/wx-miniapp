package com.jaspercloud.shipkeeper.dao.impl;

import com.jaspercloud.mybaits.mapper.definition.CommonMapper;
import com.jaspercloud.shipkeeper.dao.UserDao;
import com.jaspercloud.shipkeeper.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public void saveUser(User user) {
        commonMapper.insert(User.class, user);
    }

    @Override
    public void updateUser(User user) {
        commonMapper.updateNotNullById(User.class, user);
    }

    @Override
    public User getUserByOpenId(String openId) {
        User user = new User();
        user.setOpenId(openId);
        User result = commonMapper.selectOne(User.class, user);
        return result;
    }

    @Override
    public User getUserByUid(String uid) {
        User user = new User();
        user.setUid(uid);
        User result = commonMapper.selectOne(User.class, user);
        return result;
    }
}
