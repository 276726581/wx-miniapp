package com.jaspercloud.shipkeeper.controller;

import com.jaspercloud.shipkeeper.dto.CommentDTO;
import com.jaspercloud.shipkeeper.dto.UserDTO;
import com.jaspercloud.shipkeeper.entity.Comment;
import com.jaspercloud.shipkeeper.service.CommentService;
import com.jaspercloud.shipkeeper.service.UserService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> saveComment(@RequestBody CommentDTO comment) {
        commentService.saveComment(comment);
        ResponseEntity<Void> entity = ResponseEntity.ok().build();
        return entity;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CommentDTO>> getList(@RequestParam("commentType") String commentType,
                                                    @RequestParam("providerId") String providerId,
                                                    @RequestParam(value = "lastId", defaultValue = "0", required = false) Long lastId,
                                                    @RequestParam("count") Integer count) {
        if (lastId < 0) {
            lastId = Long.MAX_VALUE;
        }
        List<Comment> list = commentService.getListByPageAsc(commentType, providerId, lastId, count);
        List<CommentDTO> result = list.parallelStream().map(new Function<Comment, CommentDTO>() {
            @Override
            public CommentDTO apply(Comment comment) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(comment.getId());
                commentDTO.setSenderId(comment.getSenderId());
                commentDTO.setProviderId(comment.getProviderId());
                commentDTO.setContent(comment.getContent());
                commentDTO.setCommentType(comment.getCommentType());
                commentDTO.setDate(DateFormatUtils.format(comment.getCreateTime(), "yyyy-MM-dd HH:mm"));
                UserDTO userInfo = userService.getUserInfo(comment.getSenderId());
                commentDTO.setAvatarUrl(userInfo.getAvatarUrl());
                commentDTO.setNickName(userInfo.getNickName());
                return commentDTO;
            }
        }).collect(Collectors.toList());
        ResponseEntity<List<CommentDTO>> entity = ResponseEntity.ok().body(result);
        return entity;
    }
}
