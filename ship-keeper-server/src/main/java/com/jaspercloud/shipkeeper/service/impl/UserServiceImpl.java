package com.jaspercloud.shipkeeper.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaspercloud.shipkeeper.dao.UserDao;
import com.jaspercloud.shipkeeper.dto.UserDTO;
import com.jaspercloud.shipkeeper.entity.User;
import com.jaspercloud.shipkeeper.exception.HttpException;
import com.jaspercloud.shipkeeper.service.UserService;
import com.jaspercloud.shipkeeper.util.ShortUUID;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService, InitializingBean {

    private static final String appid = "wx23772a4ce511f926";
    private static final String appsecret = "c58a2ab253cce8c755ea298ca3910faf";

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    @Qualifier("shipkeeperDataSourceTransactionManager")
    @Autowired
    private DataSourceTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;

    @Override
    public void afterPropertiesSet() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.afterPropertiesSet();
    }

    @Override
    public UserDTO loginWeChatServer(String code, UserDTO userDTO) {
        Map<String, String> map = getOpenId(code);
        String sessionKey = map.get("session_key");
        String openid = map.get("openid");

        User user = transactionTemplate.execute(new TransactionCallback<User>() {
            @Override
            public User doInTransaction(TransactionStatus transactionStatus) {
                User result = userDao.getUserByOpenId(openid);
                if (null != result) {
                    result.setAvatarUrl(userDTO.getAvatarUrl());
                    result.setNickName(userDTO.getNickName());
                    userDao.updateUser(result);
                    return result;
                }

                User user = new User();
                user.setOpenId(openid);
                user.setUid(ShortUUID.generate());
                user.setAvatarUrl(userDTO.getAvatarUrl());
                user.setNickName(userDTO.getNickName());
                user.setCreateTime(new Date());
                userDao.saveUser(user);
                return user;
            }
        });

        UserDTO resultUserDTO = new UserDTO();
        resultUserDTO.setSessionKey(sessionKey);
        resultUserDTO.setOpenId(openid);
        resultUserDTO.setUid(user.getUid());
        resultUserDTO.setAvatarUrl(user.getAvatarUrl());
        resultUserDTO.setNickName(user.getNickName());
        resultUserDTO.setCreateTime(user.getCreateTime());
        return resultUserDTO;
    }

    @Override
    public UserDTO getUserInfo(String uid) {
        User result = userDao.getUserByUid(uid);
        if (null == result) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(result.getUid());
        userDTO.setAvatarUrl(result.getAvatarUrl());
        userDTO.setNickName(result.getNickName());
        return userDTO;
    }

    private Map<String, String> getOpenId(String code) {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.weixin.qq.com/sns/jscode2session")
                .queryParam("appid", appid)
                .queryParam("secret", appsecret)
                .queryParam("js_code", code)
                .queryParam("grant_type", "authorization_code")
                .build()
                .toUri();
        ResponseEntity<byte[]> entity = restTemplate.getForEntity(uri, byte[].class);
        if (HttpStatus.OK != entity.getStatusCode()) {
            throw new HttpException();
        }
        byte[] body = entity.getBody();
        String json = new String(body);
        Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());
        return map;
    }
}
