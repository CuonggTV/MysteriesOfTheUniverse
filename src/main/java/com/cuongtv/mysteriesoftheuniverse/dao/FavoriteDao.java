package com.cuongtv.mysteriesoftheuniverse.dao;

import com.cuongtv.mysteriesoftheuniverse.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FavoriteDao {
    private static String INSERT_FAVORITE_QUERY = "INSERT INTO dbo.Favorite (postId,accountId)\n" +
            "VALUES (?,?)";

    private static String DELETE_FAVORITE_QUERY = "DELETE FROM dbo.Favorite\n" +
            "WHERE postId = ? AND accountId = ?";

    private static String CHECK_FAVORITE_QUERY = "SELECT 1 FROM dbo.Favorite\n" +
            "WHERE postId = ? AND accountId = ?;";

    public static boolean createFavorite(int postId,int accountId){
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_FAVORITE_QUERY);
            ps.setInt(1,postId);
            ps.setInt(2,accountId);

            ps.executeUpdate();
            return true;

        }catch (Exception e){
            System.out.println("Cannot save favorite!");
            System.out.println(" -- "+e);
        }
        return false;
    }

    public static boolean deleteFavorite(int postId,int accountId){
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_FAVORITE_QUERY);
            ps.setInt(1,postId);
            ps.setInt(2,accountId);

            ps.executeUpdate();
            return true;

        }catch (Exception e){
            System.out.println("Cannot delete favorite!");
            System.out.println(" -- "+e);
        }
        return false;
    }

    public static boolean checkFavorite(int postId,int accountId){
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(CHECK_FAVORITE_QUERY);
            ps.setInt(1,postId);
            ps.setInt(2,accountId);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        }catch (Exception e){
            System.out.println("Cannot save to favorite!");
            System.out.println(" -- "+e);
        }
        return false;
    }



}
