package com.cuongtv.mysteriesoftheuniverse.dao;

import com.cuongtv.mysteriesoftheuniverse.entities.Friendship;
import com.cuongtv.mysteriesoftheuniverse.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDao {
    private static String GET_FRIEND_REQUEST_BY_ID =
            "SELECT a.name AS accountReceivedName,a.introduction , fs.accountRequest, fs.accountReceived, fs.isAccepted\n" +
                    "FROM dbo.Friendship AS fs\n" +
                    "JOIN dbo.Account AS a ON a.id = fs.accountRequest\n" +
                    "WHERE fs.accountReceived = ? AND fs.isAccepted = 0";
    private static String GET_FRIEND_BY_ID =
            "SELECT a.name AS accountReceivedName,a.introduction , fs.accountRequest, fs.accountReceived, fs.isAccepted\n" +
                    "FROM dbo.Friendship AS fs\n" +
                    "JOIN dbo.Account AS a ON (a.id = fs.accountReceived OR a.id = fs.accountRequest) AND a.id != ?\n" +
                    "   WHERE (fs.accountReceived = ? OR fs.accountRequest = ?) \n" +
                    "   AND fs.isAccepted = 1";
    private static String DELETE_FRIEND_BY_ID = "DELETE FROM Friendship WHERE " +
                "(accountReceived = ? AND accountRequest = ?)" +
                "OR (accountRequest = ? AND accountReceived = ?);";

    private static String UPDATE_FRIEND_REQUEST_QUERY = "UPDATE dbo.Friendship\n" +
            "SET isAccepted = ?\n" +
            "WHERE accountReceived = ? AND accountRequest = ?;";
    private static String INSERT_FRIENDSHIP_QUERY = "INSERT INTO [Friendship] ([accountRequest], [accountReceived], [isAccepted])\n" +
            "VALUES (?, ?, 0);";
    private static Friendship setFriendship(ResultSet resultSet) throws SQLException {
        Friendship friendship = new Friendship();
        friendship.setAccountReceived(resultSet.getInt("accountReceived"));
        friendship.setAccountRequest(resultSet.getInt("accountRequest"));
        friendship.setIsAccepted(resultSet.getBoolean("isAccepted"));
        friendship.setAccountRequestName(resultSet.getString("accountReceivedName"));
        friendship.setIntroduction(resultSet.getNString("introduction"));
        return friendship;
    }
    public static List<Friendship> getFriendRequestById(int accountId) throws ClassNotFoundException, SQLException {
        List<Friendship> friendships = new ArrayList<>();
        Connection connection = DatabaseUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_FRIEND_REQUEST_BY_ID);
        statement.setString(1, String.valueOf(accountId));

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            friendships.add(setFriendship(resultSet));
        }
        return friendships;
    }
    public static List<Friendship> getFriendById(int accountId) throws SQLException, ClassNotFoundException {
        List<Friendship> friendships = new ArrayList<>();
        Connection connection = DatabaseUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_FRIEND_BY_ID);

        statement.setString(1, String.valueOf(accountId));
        statement.setString(2, String.valueOf(accountId));
        statement.setString(3, String.valueOf(accountId));


        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            friendships.add(setFriendship(resultSet));
        }
        return friendships;
    }
    public static void deleteFriendship(int accountReceive, int accountRequest){
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_FRIEND_BY_ID);
            statement.setString(1, String.valueOf(accountReceive));
            statement.setString(2, String.valueOf(accountRequest));

            statement.setString(3, String.valueOf(accountReceive));
            statement.setString(4, String.valueOf(accountRequest));
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Cannot delete friendship!");
            System.out.println(" -- "+e);
        }
    }

    public static void updateFriendship(int accountReceive, int accountRequest,boolean isAccepted){
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_FRIEND_REQUEST_QUERY);
            statement.setBoolean(1, isAccepted);
            statement.setInt(2, accountReceive);
            statement.setInt(3, accountRequest);

            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Cannot accept friend request!");
            System.out.println(" -- "+e);
        }

    }

    public static void sendFriendRequest(int accountReceived, int accountRequest){
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_FRIENDSHIP_QUERY);
            statement.setInt(1,accountRequest);
            statement.setInt(2,accountReceived);

            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Cannot send friendship!");
            System.out.println(" -- "+e);
        }

    }


}
