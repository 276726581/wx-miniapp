package com.jaspercloud.shipkeeper.service;

import com.jaspercloud.shipkeeper.dto.CommentDTO;
import com.jaspercloud.shipkeeper.entity.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(transactionManager = "shipkeeper")
public interface CommentService {

    void saveComment(CommentDTO commentDTO);

    List<Comment> getListByPageAsc(String commentType, String providerId, long lastId, int count);
}
