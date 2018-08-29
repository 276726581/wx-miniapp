package com.jaspercloud.shipkeeper.dao;

import com.jaspercloud.shipkeeper.entity.User;

public interface UserDao {

    void saveUser(User user);

    void updateUser(User user);

    User getUserByOpenId(String openId);

    User getUserByUid(String uid);
}
