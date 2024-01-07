package com.cuongtv.mysteriesoftheuniverse.dao;

import com.cuongtv.mysteriesoftheuniverse.entities.Message;
import com.cuongtv.mysteriesoftheuniverse.utils.DatabaseUtils;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    private static String GET_MESSAGE_BY_ID_QUERY = "SELECT accountSent,accountReceived, groupId,details,timeSent\n" +
            "FROM dbo.Message\n" +
            "WHERE (accountSent = ? OR accountReceived = ?)" +
            "AND  (accountSent = ? OR accountReceived = ?)";
    private static String GET_MESSAGE_BY_GROUP_ID_QUERY = "SELECT accountSent,accountReceived, groupId,details,timeSent\n" +
            "FROM dbo.Message\n" +
            "WHERE (accountSent = ? OR accountReceived = ?)\n" +
            "AND  groupId = ?";
    private static String INSERT_MESSAGE_QUERY = "INSERT INTO dbo.Message(accountSent, accountReceived, groupId, details, timeSent)\n" +
            "VALUES(?, ?, ?, ?,GETDATE());";

    private static Message setMessage(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setAccountSent(rs.getInt("accountSent"));
        message.setAccountReceived(rs.getInt("accountReceived"));
        message.setGroupId(rs.getInt("groupId"));
        message.setDetails(rs.getNString("details"));
        message.setTimeSent(rs.getString("timeSent"));
        return message;
    }
    public static List<Message> getMessageById(int sentId, int receivedId) {
        List<Message> messages= new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_MESSAGE_BY_ID_QUERY);
            statement.setString(1,String.valueOf(sentId));
            statement.setString(2,String.valueOf(sentId));
            statement.setString(3,String.valueOf(receivedId));
            statement.setString(4,String.valueOf(receivedId));

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                messages.add(setMessage(rs));
            }
        }catch (Exception e){
            System.out.println("Cannot get message by id!");
            System.out.println(" -- "+e);
        }
        return messages;
    }

    public static List<Message> getMessageByGroupId(int sentId, int groupId){
        List<Message> messages= new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_MESSAGE_BY_GROUP_ID_QUERY);
            statement.setString(1,String.valueOf(sentId));
            statement.setString(2,String.valueOf(sentId));
            statement.setString(3,String.valueOf(groupId));

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                messages.add(setMessage(rs));
            }
        }catch (Exception e){
            System.out.println("Cannot get message by groupId!");
            System.out.println(" -- "+e);
        }

        return messages;
    }


    public static void createMessage(Message message) {
        try{
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_MESSAGE_QUERY);
            statement.setNString(1, String.valueOf(message.getAccountSent()));
            if (message.getAccountReceived()==0){
                statement.setNull(2,Types.INTEGER);
            }
            else {
                statement.setString(2, String.valueOf(message.getAccountReceived()));

            }
            if (message.getGroupId()==0){
                statement.setNull(3,Types.INTEGER);

            }
            else {
                statement.setString(3, String.valueOf(message.getGroupId()));
            }

            statement.setNString(4,message.getDetails());

            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("Cannot create message!");
            System.out.println(" -- "+e);
        }
    }
}
