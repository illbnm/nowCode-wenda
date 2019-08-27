package com.nowcoder.dao;

import com.nowcoder.model.Comment;
import com.nowcoder.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, content, created_date, entity_id , entity_type,status ";
    String SELECT_FIELDS = " id , " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({" select ", SELECT_FIELDS, " from ", TABLE_NAME, "where entity_Id=#{entityId} and entity_Type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

//    List<Question> selectLatestQuestions(@Param("userId") int userId,
//                                        @Param("offset") int offset,
//                                        @Param("limit") int limit);

    @Select({" select count(id) from ", TABLE_NAME, " where entity_Id=#{entityId} and entity_Type=#{entityType} "})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Update({" update  comment set status=#{status} where id=#{id} "})
    int updatesStatus(@Param("id") int id, @Param("status") int status);

    @Select({" select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"}
    )
    public Comment getCommentById(@Param("id") int id);
}
