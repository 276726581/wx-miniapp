package com.jaspercloud.shipkeeper.dao.mapper.shipkeeper;

import com.jaspercloud.shipkeeper.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface CommentMapper {

    @SelectProvider(type = Provider.class, method = "selectCommentListByPageDesc")
    List<Comment> selectCommentListByPageDesc(Class<Comment> clazz,
                                              @Param("commentType") String commentType,
                                              @Param("providerId") String providerId,
                                              @Param("lastId") Long lastId,
                                              int count);

    class Provider {

        public String selectCommentListByPageDesc(Class<Comment> clazz,
                                                  @Param("commentType") String commentType,
                                                  @Param("providerId") String providerId,
                                                  @Param("lastId") Long lastId,
                                                  int count) {
            StringBuilder builder = new StringBuilder();
            builder.append("select * from").append(" comment")
                    .append(" where comment_type=#{commentType} and provider_id=#{providerId}");
            if (lastId > 0) {
                builder.append(" and id<#{lastId}");
            }
            builder.append(" order by id desc")
                    .append(" limit ").append(count);
            String sql = builder.toString();
            return sql;
        }
    }
}
