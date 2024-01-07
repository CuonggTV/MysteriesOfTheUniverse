package com.cuongtv.mysteriesoftheuniverse.dao;

import com.cuongtv.mysteriesoftheuniverse.entities.Post;
import com.cuongtv.mysteriesoftheuniverse.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class PostLikeDao {
    private static String GET_POST_LIKE_QUERY = "SELECT COUNT(pl.id) AS Likes\n" +
            "FROM PostLike AS pl\n" +
            "WHERE pl.postId = ?\n" +
            "GROUP BY pl.postId\n" +
            "ORDER BY Likes DESC;";

    private static String UPDATE_POST_LIKE_QUERY = "MERGE INTO [PostLike] AS pl\n" +
            "USING (SELECT 1 AS dummy) AS src\n" +
            "ON  pl.postId = ? AND pl.accountId = ?\n" +
            "WHEN MATCHED THEN\n" +
            "  UPDATE SET pl.isLiked = CASE WHEN pl.isLiked = 1 THEN 0 ELSE 1 END\n" +
            "WHEN NOT MATCHED THEN\n" +
            "  INSERT (postId, accountId, isLiked)\n" +
            "  VALUES (?, ?, 1);";

    private static String CHECK_ISLIKED_QUERY = "SELECT 1\n" +
            "FROM dbo.PostLike AS pl\n" +
            "WHERE postId = ? AND accountId = ? AND isLiked = 1;";
    public static int getPostLike(int postId){
        int likes = 0;
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement stmt = connection.prepareStatement(GET_POST_LIKE_QUERY);
            stmt.setInt(1,postId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                likes = rs.getInt("Likes");
            }
        }
        catch (Exception exception){
            System.out.println("Cannot get post likes");
        }
        return likes;
    }

    public static boolean checkIsLikedOnPostId(int postId,int accountId){
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement stmt = connection.prepareStatement(CHECK_ISLIKED_QUERY);
            stmt.setInt(1,postId);
            stmt.setInt(2,accountId);

            ResultSet rs = stmt.executeQuery();

           return rs.next();

        }
        catch (Exception e){
            System.out.println("Cannot get isLiked in PostLike!");
            System.out.println(" -- "+e);
        }
        return false;
    }

    public static void updatePostLike(int postId,int accountId){
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement stmt = connection.prepareStatement(UPDATE_POST_LIKE_QUERY);
            stmt.setInt(1,postId);
            stmt.setInt(2,accountId);
            stmt.setInt(3,postId);
            stmt.setInt(4,accountId);

            stmt.executeUpdate();

        }
        catch (Exception exception){
            System.out.println("Cannot get post likes");
        }
    }
}
