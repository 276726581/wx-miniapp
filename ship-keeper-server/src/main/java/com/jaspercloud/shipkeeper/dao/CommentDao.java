package com.jaspercloud.shipkeeper.dao;

import com.jaspercloud.shipkeeper.entity.Comment;

import java.util.List;

public interface CommentDao {

    void insertComment(Comment comment);

    List<Comment> selectListByPageAsc(String commentType, String providerId, long lastId, int count);
}
