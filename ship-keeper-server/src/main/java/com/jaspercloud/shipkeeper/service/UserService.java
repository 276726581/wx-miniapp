package com.jaspercloud.shipkeeper.service;

import com.jaspercloud.shipkeeper.dto.UserDTO;

public interface UserService {

    UserDTO loginWeChatServer(String code, UserDTO userDTO);

    UserDTO getUserInfo(String uid);
}
