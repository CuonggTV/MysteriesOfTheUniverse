package com.cuongtv.mysteriesoftheuniverse.dao;

import com.cuongtv.mysteriesoftheuniverse.entities.Notification;
import com.cuongtv.mysteriesoftheuniverse.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationDao {
    private static String GET_NOTIFICATION_BY_ACCOUNT_ID_QUERY = "SELECT n.details, n.accountSendId ,a.name, a.avatarName, t.url\n" +
            "FROM dbo.Notification AS n\n" +
            "JOIN dbo.Account AS a ON a.id = n.accountSendId\n" +
            "JOIN dbo.NotificationType AS t ON t.type = n.type\n" +
            "WHERE accountId = ?";

    private static String DELETE_NOTIFICATION_QUERY = "DELETE FROM dbo.Notification \n" +
            "WHERE accountId = ? AND accountSendId = ? AND details = ? ;";

    private static Notification setNotification(ResultSet resultSet) throws SQLException {
        Notification notification = new Notification();
        notification.setDetails(resultSet.getString("details"));
        notification.setAccountSentId(resultSet.getInt("accountSendId"));
        notification.setAvatarName(resultSet.getString("avatarName"));
        notification.setAccountSentName(resultSet.getNString("name"));
        notification.setUrl(resultSet.getString("url"));

        return notification;
    }
    public static List<Notification> getNotificationByAccountId(int accountId){
        List<Notification> notificationList = new ArrayList<>();
        try{
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_NOTIFICATION_BY_ACCOUNT_ID_QUERY);
            statement.setInt(1,accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Notification notification = setNotification(resultSet);

                if(notification.getUrl().equals("profile-post?id=")){
                    notification.setUrl(notification.getUrl() + accountId);
                }

                notificationList.add(notification);
            }
        }
        catch (Exception e){
            System.out.println("Cannot get notification!");
            System.out.println(" -- "+e);
        }
        Collections.reverse(notificationList);
        return notificationList;

    }
    public static boolean removeNotification(int accountId,Notification notification){
        try{
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_NOTIFICATION_QUERY);
            statement.setInt(1,accountId);
            statement.setInt(2,notification.getAccountSentId());
            statement.setString(3,notification.getDetails());

            statement.executeUpdate();
           return true;
        }
        catch (Exception e){
            System.out.println("Cannot remove notification!");
            System.out.println(" -- "+e);
        }
        return false;
    }
}
