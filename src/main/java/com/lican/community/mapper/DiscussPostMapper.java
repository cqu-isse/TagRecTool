package com.lican.community.mapper;

import com.lican.community.entity.DiscussPostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    List<DiscussPostEntity> selectDiscussPosts(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPostEntity discussPost);

    DiscussPostEntity selectDiscussPostById(@Param("id") int id);

    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}
