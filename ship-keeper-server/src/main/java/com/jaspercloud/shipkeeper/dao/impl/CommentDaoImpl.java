package com.jaspercloud.shipkeeper.dao.impl;

import com.jaspercloud.mybaits.mapper.definition.CommonMapper;
import com.jaspercloud.shipkeeper.dao.CommentDao;
import com.jaspercloud.shipkeeper.dao.mapper.shipkeeper.CommentMapper;
import com.jaspercloud.shipkeeper.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void insertComment(Comment comment) {
        commonMapper.insert(Comment.class, comment);
    }

    @Override
    public List<Comment> selectListByPageAsc(String commentType, String providerId, long lastId, int count) {
        List<Comment> list = commentMapper.selectCommentListByPageDesc(Comment.class, commentType, providerId, lastId, count);
        return list;
    }
}
