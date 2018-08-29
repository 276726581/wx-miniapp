package com.jaspercloud.shipkeeper.service.impl;

import com.jaspercloud.shipkeeper.dao.CommentDao;
import com.jaspercloud.shipkeeper.dto.CommentDTO;
import com.jaspercloud.shipkeeper.entity.Comment;
import com.jaspercloud.shipkeeper.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public void saveComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setSenderId(commentDTO.getSenderId());
        comment.setProviderId(commentDTO.getProviderId());
        comment.setContent(commentDTO.getContent());
        comment.setCreateTime(new Date());
        comment.setCommentType(commentDTO.getCommentType());
        commentDao.insertComment(comment);
    }

    @Override
    public List<Comment> getListByPageAsc(String commentType, String providerId, long lastId, int count) {
        List<Comment> list = commentDao.selectListByPageAsc(commentType, providerId, lastId, count);
        return list;
    }
}
