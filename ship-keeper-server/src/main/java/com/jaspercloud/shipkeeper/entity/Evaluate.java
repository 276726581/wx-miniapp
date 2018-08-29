package com.jaspercloud.shipkeeper.entity;


import com.jaspercloud.mybaits.mapper.annotation.SubTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@SubTable
@Entity(name = "evaluate")
public class Evaluate {

    @GeneratedValue(generator = "select nextval('seq_evaluate')")
    @Id
    private Long id;
    private Integer type;
    @Column(name = "rel_id")
    private Long relId;
    @Column(name = "rater_id")
    private Long raterId;
    @Column(name = "client_id")
    private Long clientId;
    private Integer score;
    private String content;
    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getRelId() {
        return relId;
    }

    public void setRelId(Long relId) {
        this.relId = relId;
    }

    public Long getRaterId() {
        return raterId;
    }

    public void setRaterId(Long raterId) {
        this.raterId = raterId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public Evaluate() {
    }
}
