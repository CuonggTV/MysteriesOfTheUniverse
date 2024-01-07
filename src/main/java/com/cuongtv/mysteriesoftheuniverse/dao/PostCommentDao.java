package com.cuongtv.mysteriesoftheuniverse.dao;

import com.cuongtv.mysteriesoftheuniverse.entities.Comment;
import com.cuongtv.mysteriesoftheuniverse.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostCommentDao {
    private static String INSERT_COMMENT_QUERY = "INSERT INTO dbo.PostComment(postId,accountId,details,timeSent)\n" +
            "VALUES(?,?,?,GETDATE())";
    private static String GET_COMMENTS_BY_POST_ID_QUERY = "SELECT pm.postId,pm.accountId, a.name,pm.details,pm.timeSent, a.avatarName\n" +
            "FROM dbo.PostComment AS pm\n" +
            "JOIN dbo.Account AS a ON a.id = pm.accountId\n" +
            "WHERE pm.postId = ?;";
    private static Comment setComment(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setPostId(resultSet.getInt("postId"));
        comment.setAccountId(resultSet.getInt("accountId"));
        comment.setAccountName(resultSet.getNString("name"));
        comment.setDetails(resultSet.getNString("details"));
        comment.setTimeSent(resultSet.getString("timeSent"));
        comment.setAvatarPath(resultSet.getString("avatarName"));
        return comment;
    }
    public static List<Comment> getCommentsByPostId(int postId){
        List<Comment> commentList = new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement =connection.prepareStatement(GET_COMMENTS_BY_POST_ID_QUERY);
            statement.setInt(1,postId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Comment comment = setComment(resultSet);
                commentList.add(comment);
            }
        }
        catch (Exception e){
            System.out.println("Cannot get post comment!");
            System.out.println(" -- " + e);
        }
        return commentList;
    }
    public static void createComment(Comment comment){
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement =connection.prepareStatement(INSERT_COMMENT_QUERY);
            statement.setInt(1,comment.getPostId());
            statement.setInt(2,comment.getAccountId());
            statement.setNString(3,comment.getDetails());
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Cannot create comment");
            System.out.println(" -- " + e);
        }
    }

}
