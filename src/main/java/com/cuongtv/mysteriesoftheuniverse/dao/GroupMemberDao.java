package com.cuongtv.mysteriesoftheuniverse.dao;

import com.cuongtv.mysteriesoftheuniverse.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GroupMemberDao {
    private static String INSERT_GROUP_MEMBER_QUERY = "INSERT INTO dbo.GroupMember (groupId,accountMember,dateJoin)\n" +
            "VALUES( ?,?,GETDATE())";
    private static String DELETE_GROUP_MEMBER_QUERY = "DELETE FROM GroupMember WHERE groupId = ? AND accountMember = ?;";

    public static void removeGroupMember(int groupId,int accountId){
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_GROUP_MEMBER_QUERY);
            statement.setInt(1,groupId);
            statement.setInt(2,accountId);
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Cannot remove group member!");
            System.out.println(" -- "+e);
        }
    }

    public static void insertGroupMember(int groupId, int accountId) {
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_GROUP_MEMBER_QUERY);
            ps.setString(1, String.valueOf(groupId));
            ps.setString(2, String.valueOf(accountId));
            ps.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Cannot insert group member!");
            System.out.println(" -- "+e);
        }
    }
}
