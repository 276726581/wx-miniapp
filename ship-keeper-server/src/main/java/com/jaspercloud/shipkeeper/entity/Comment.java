package com.jaspercloud.shipkeeper.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "comment")
public class Comment {

    @GeneratedValue(generator = "select nextval('seq_comment')")
    @Id
    private Long id;
    @Column(name = "sender_id")
    private String senderId;
    @Column(name = "provider_id")
    private String providerId;
    private String content;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "comment_type")
    private String commentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public Comment() {
    }
}
