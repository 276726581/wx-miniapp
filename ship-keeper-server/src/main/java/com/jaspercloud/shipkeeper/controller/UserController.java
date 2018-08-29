package com.jaspercloud.shipkeeper.controller;

import com.jaspercloud.shipkeeper.dto.UserDTO;
import com.jaspercloud.shipkeeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/onLogin/{code}")
    public ResponseEntity<UserDTO> getUserId(@PathVariable("code") String code, @RequestBody UserDTO userDTO) {
        UserDTO resultUserDTO = userService.loginWeChatServer(code, userDTO);
        ResponseEntity<UserDTO> entity = ResponseEntity.ok().body(resultUserDTO);
        return entity;
    }

    @GetMapping("/{uid}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable("uid") String uid) {
        UserDTO userInfo = userService.getUserInfo(uid);
        ResponseEntity<UserDTO> entity = ResponseEntity.ok().body(userInfo);
        return entity;
    }
}
