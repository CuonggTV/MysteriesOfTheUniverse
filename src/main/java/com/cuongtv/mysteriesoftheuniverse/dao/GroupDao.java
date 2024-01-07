package com.cuongtv.mysteriesoftheuniverse.dao;

import com.cuongtv.mysteriesoftheuniverse.entities.Group;
import com.cuongtv.mysteriesoftheuniverse.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    private static String GET_GROUP_BY_ACCOUNT_ID_QUERY = "SELECT g.groupId, g.name AS groupName, g.accountOwner,a.name AS accountName, g.details, g.dateCreated,g.approvement\n" +
            "FROM dbo.[Group] AS g\n" +
            "JOIN dbo.Account AS a ON a.id = g.accountOwner\n" +
            "WHERE g.accountOwner = ?;";
        private static String GET_ALL_GROUPS_QUERY = "SELECT g.groupId, g.name AS groupName, g.accountOwner,a.name AS accountName, g.details, g.dateCreated,g.approvement\n" +
                "FROM dbo.[Group] AS g\n" +
                "JOIN dbo.Account AS a ON a.id = g.accountOwner\n";
    private static String GET_GROUP_BY_ID_QUERY = "SELECT g.groupId, g.name AS groupName, g.accountOwner,a.name AS accountName, g.details, g.dateCreated,g.approvement\n" +
            "FROM dbo.[Group] AS g\n" +
            "JOIN dbo.Account AS a ON a.id = g.accountOwner\n"+
            "WHERE g.groupId = ?;";
    private static String CHECK_GROUP_BY_NAME_QUERY = "SELECT 1 FROM dbo.[Group]\n" +
            "WHERE name = ?;";
    private static String INSERT_GROUP_QUERY = "INSERT INTO [Group] ([accountOwner], [name], [details],[approvement], [dateCreated])\n" +
            "VALUES\n" +
            "(?,?, ?,?, GETDATE());";

    private static String GET_GROUP_ACCOUNT_NOT_JOIN_QUERY = "SELECT g.groupId, g.name AS groupName, g.accountOwner,a.name AS accountName, g.details, g.dateCreated,g.approvement\n" +
            "FROM [Group] AS g\n" +
            "JOIN dbo.Account AS a ON a.id = g.accountOwner\n" +
            "WHERE NOT EXISTS (\n" +
            "    SELECT 1\n" +
            "    FROM [GroupMember] gm\n" +
            "    WHERE gm.[groupId] = g.[groupId]\n" +
            "    AND gm.[accountMember] = ?\n" +
            ") AND a.id != ?;";
    private static String GET_GROUP_ACCOUNT_JOIN_QUERY = "SELECT g.groupId, g.name AS groupName, g.accountOwner,a.name AS accountName, g.details, g.dateCreated,g.approvement\n" +
            "FROM [Group] AS g\n" +
            "JOIN dbo.Account AS a ON a.id = g.accountOwner\n" +
            "WHERE EXISTS (\n" +
            "    SELECT 1\n" +
            "    FROM [GroupMember] gm\n" +
            "    WHERE gm.[groupId] = g.[groupId]\n" +
            "    AND gm.[accountMember] = ?\n" +
            ") OR a.id = ?;";
    private static String GET_SEE_JOIN_GROUP_ACCOUNT_JOIN_QUERY = "SELECT g.groupId, g.name AS groupName, g.accountOwner,a.name AS accountName, g.details, g.dateCreated,g.approvement\n" +
            "FROM [Group] AS g\n" +
            "JOIN dbo.Account AS a ON a.id = g.accountOwner\n" +
            "WHERE EXISTS (\n" +
            "    SELECT 1\n" +
            "    FROM [GroupMember] gm\n" +
            "    WHERE gm.[groupId] = g.[groupId]\n" +
            "    AND gm.[accountMember] = ?\n" +
            ");";
    private static String UPDATE_GROUP_QUERY = "UPDATE dbo.[Group]\n" +
            "SET name =  ? ,details = ? , approvement = ?\n" +
            "WHERE groupId = ?;";
    private static Group setGroup(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setId(rs.getInt("groupId"));
        group.setAccountName(rs.getString("accountName"));
        group.setName(rs.getString("groupName"));
        group.setDetails(rs.getNString("details"));
        group.setDateCreated(rs.getString("dateCreated"));
        group.setAccountOwner(rs.getInt("accountOwner"));
        group.setApprove(rs.getBoolean("approvement"));
        return group;
    }
    public static List<Group> getGroupByAccountId(int accountId) {
        List<Group> groupList = new ArrayList<>();
        try{
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_GROUP_BY_ACCOUNT_ID_QUERY);
            ps.setString(1,String.valueOf(accountId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Group group = setGroup(rs);
                groupList.add(group);
            }
        }catch (Exception e){
            System.out.println("Cannot get my group!");
            System.out.println(" -- " + e);
        }

        return groupList;

    }
    public static List<Group> getAllGroups() throws SQLException, ClassNotFoundException {
        List<Group> groupList = new ArrayList<>();
        Connection connection = DatabaseUtils.getConnection();
        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery(GET_ALL_GROUPS_QUERY);
        while (rs.next()){
            Group group = setGroup(rs);
            groupList.add(group);
        }
        return groupList;
    }

    public static boolean checkGroupName(String name) throws ClassNotFoundException, SQLException {
        Connection connection = DatabaseUtils.getConnection();
        PreparedStatement ps = connection.prepareStatement(CHECK_GROUP_BY_NAME_QUERY);
        ps.setString(1,name);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }
    public static boolean createGroup(Group group){
        try{
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_GROUP_QUERY);
            ps.setInt(1, group.getAccountOwner());
            ps.setString(2, group.getName());
            ps.setNString(3, group.getDetails());
            ps.setBoolean(4,group.getApprove());

            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Cannot create group!");
            System.out.println(" -- "+e);
            return false;
        }
        return true;
    }

    public static Group getGroupById(String id){
        Group group = new Group();
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_GROUP_BY_ID_QUERY);
            ps.setString(1, String.valueOf(id));
            ResultSet rs = ps.executeQuery();


            while (rs.next()){
                group = setGroup(rs);
            }
        }catch (Exception e){
            System.out.println("Cannot get group by id!");
            System.out.println(" -- "+e);
        }

        return group;
    }

    public static List<Group> getNotJoinGroup(int accountId){
        List<Group> groupList = new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_GROUP_ACCOUNT_NOT_JOIN_QUERY);
            ps.setString(1,String.valueOf(accountId));
            ps.setString(2,String.valueOf(accountId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Group group = setGroup(rs);
                groupList.add(group);
            }
        }catch (Exception e){
            System.out.println("Cannot get my group!");
            System.out.println(" -- " + e);
        }

        return groupList;
    }

    public static List<Group> getJoinGroup(int accountId){
        List<Group> groupList = new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_GROUP_ACCOUNT_JOIN_QUERY);
            ps.setInt(1,accountId);
            ps.setInt(2,accountId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Group group = setGroup(rs);
                groupList.add(group);
            }
        }catch (Exception e){
            System.out.println("Cannot get joined group!");
            System.out.println(" -- " + e);
        }
        return groupList;
    }
    public static List<Group> getSeeJoinGroup(int accountId){
        List<Group> groupList = new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_SEE_JOIN_GROUP_ACCOUNT_JOIN_QUERY);
            ps.setInt(1,accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Group group = setGroup(rs);
                groupList.add(group);
            }
        }catch (Exception e){
            System.out.println("Cannot get joined group!");
            System.out.println(" -- " + e);
        }
        return groupList;
    }

    public static boolean updateGroup(Group group){
        try{
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_GROUP_QUERY);
            ps.setString(1, group.getName());
            ps.setString(2, group.getDetails());
            ps.setBoolean(3, group.getApprove());
            ps.setInt(4,group.getId());

            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Cannot create group!");
            System.out.println(" -- "+e);
            return false;
        }
        return true;
    }
}
