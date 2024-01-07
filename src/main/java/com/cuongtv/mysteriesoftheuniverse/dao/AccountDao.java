package com.cuongtv.mysteriesoftheuniverse.dao;

import com.cuongtv.mysteriesoftheuniverse.dto.RegisterDto;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
import com.cuongtv.mysteriesoftheuniverse.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private static final String GET_ALL_ACCOUNTS_QUERY = "Select * from Account;";
    private static final String CHECK_ACCOUNT_EXIST_BY_EMAIL_QUERY = "Select 1 from Account WHERE email = ? AND id != ?;";
    private static final String CHECK_EMAIL_EXIST_QUERY = "Select 1 from Account WHERE email = ?;";

    private static final String CHECK_ACCOUNT_EXIST_BY_USERNAME_QUERY = "Select 1 from Account WHERE username = ? AND id != ?;";
    private static final String CHECK_USERNAME_EXIST_QUERY = "Select 1 from Account WHERE username = ?;";
    private  static final String GET_ACCOUNT_BY_LOGIN_QUERY = "Select * from Account WHERE username = ? ;";
    private  static final String GET_ACCOUNT_BY_ID_QUERY = "Select * from Account WHERE id = ? ;";
    private static final String INSERT_ACCOUNT_QUERY =
            "INSERT INTO [Account] ([username], [password], [name], [email], [phoneNumber], [dateOfBirth], [dateCreated], [avatarName])\n" +
            "VALUES\n" +
            "  (?,?,?,?,?,?, GETDATE(), '\\images\\avatar\\default.png')";
    private static final String GET_ACCOUNT_IN_FRIENDSHIP_WITH_ID_QUERY ="SELECT a.id, a.name, a.introduction, a.avatarName\n" +
            "FROM Account AS a\n" +
            "LEFT JOIN Friendship f1 ON a.id = f1.accountRequest AND f1.accountReceived = ? AND f1.isAccepted = 1\n" +
            "LEFT JOIN Friendship f2 ON a.id = f2.accountReceived AND f2.accountRequest = ? AND f2.isAccepted = 1\n" +
            "WHERE f1.accountRequest IS NOT NULL OR f2.accountReceived IS NOT NULL \n" +
            "AND a.id != ?;";
    private static final String GET_ACCOUNT_NOT_IN_FRIENDSHIP_WITH_ID_QUERY = "SELECT a.id, a.name, a.introduction, a.avatarName\n" +
            "FROM dbo.Account a\n" +
            "WHERE  a.id NOT IN (\n" +
            "\tSELECT accountReceived\n" +
            "    FROM dbo.Friendship\n" +
            "\t WHERE accountRequest = ? \n" +
            ")\n" +
            "AND a.id NOT IN (\n" +
            "\tSELECT accountRequest\n" +
            "    FROM dbo.Friendship\n" +
            "\t WHERE accountReceived = ? \n" +
            ")" +
            " AND a.id != ?;";
    private static final String GET_ACCOUNTS_SEND_FRIEND_REQUEST_QUERY = "SELECT a.id, a.name, a.introduction, a.avatarName\n" +
            "FROM Account AS a\n" +
            "JOIN Friendship fs ON fs.accountReceived = ? AND fs.isAccepted = 0\n" +
            "WHERE a.id = fs.accountRequest";

    private static final String GET_ACCOUNTS_GET_FRIEND_REQUEST_QUERY = "SELECT a.id, a.name, a.introduction, a.avatarName\n" +
            "FROM Account AS a\n" +
            "JOIN Friendship fs ON fs.accountRequest = ? AND fs.isAccepted = 0\n" +
            "WHERE a.id = fs.accountReceived";
    private static final String GET_GROUP_MEMBER_ACCOUNTS_QUERY = "SELECT a.id, a.name, a.introduction, a.avatarName\n" +
            "            FROM Account AS a\n" +
            "            JOIN GroupMember gm ON gm.accountMember = a.id\n" +
            "            WHERE gm.groupId = ?";
    private static final String UPDATE_ACCOUNT_QUERY =
            "UPDATE dbo.Account\n" +
                    "SET username = ?, name = ?, email = ?, phoneNumber = ?,\n" +
                    "dateOfBirth = ?, introduction = ?, interest = ?, avatarName = ?\n" +
                    "WHERE id=?;";

    private static final String UPDATE_PASSWORD_QUERY =
            "UPDATE dbo.Account\n" +
                    "SET password = ?\n" +
                    "WHERE id=?;";
    private static final String UPDATE_AVATAR_QUERY =
            "UPDATE dbo.Account\n" +
                    "SET avatarName = ?\n" +
                    "WHERE id=?;";
    private static Account setAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setUsername(resultSet.getString("username"));
        account.setPassword(resultSet.getString("password"));
        account.setName(resultSet.getNString("name"));
        account.setEmail(resultSet.getString("email"));
        account.setPhoneNumber(resultSet.getString("phoneNumber"));
        account.setDateOfBirth(resultSet.getString("dateOfBirth"));
        account.setDateCreated(resultSet.getString("dateCreated"));
        account.setIntroduction(resultSet.getNString("introduction"));
        account.setInterest(resultSet.getString("interest"));
        account.setDeleted(resultSet.getBoolean("isDeleted"));
        account.setAvatarName(resultSet.getString("avatarName"));
        return account;
    }
    public static Account getAccountById(int id){
        Account account = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_ID_QUERY);
            statement.setString(1,String.valueOf(id));
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                if(rs.getInt("id")==id){
                    account = setAccount(rs);
                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Cannot account by id!");
            System.out.println(" -- "+e);
        }
        return account;
    }

    public static List<Account> getAllAccounts() throws SQLException, ClassNotFoundException {
        List<Account> accountList = new ArrayList<>();
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_ALL_ACCOUNTS_QUERY);
        while (resultSet.next()){
            accountList.add(setAccount(resultSet));
        }
        return accountList;
    }
    public static boolean isEmailExisted(String email,int id){
        try{
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt;
            if (id == 0 ){
                stmt = conn.prepareStatement(CHECK_EMAIL_EXIST_QUERY);
                stmt.setString(1, email);
            }
            else{
                stmt = conn.prepareStatement(CHECK_ACCOUNT_EXIST_BY_EMAIL_QUERY);
                stmt.setString(1, email);
                stmt.setInt(2, id);
            }


            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch (Exception e){
            System.out.println("Cannot check is email exist!");
            System.out.println(" -- "+e.getMessage());
        }
        return false;
    }

    public static boolean isUsernameExisted(String username,int id) {
        try{
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt;
            if (id == 0){
               stmt = conn.prepareStatement(CHECK_USERNAME_EXIST_QUERY);
               stmt.setString(1, username);
            }
            else {
                stmt = conn.prepareStatement(CHECK_ACCOUNT_EXIST_BY_USERNAME_QUERY);
                stmt.setString(1, username);
                stmt.setString(2, String.valueOf(id));
            }

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        }catch (Exception e){
            System.out.println("Cannot check username!");
            System.out.println(" -- "+e);
        }
       return false;
    }

    public static void createAccount(RegisterDto dto) {
        try {
                Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_ACCOUNT_QUERY);
                stmt.setString(1, dto.getUsername());
                stmt.setString(2, dto.getPassword());
                stmt.setNString(3, dto.getName());
                stmt.setString(4, dto.getEmail());
                stmt.setString(5, dto.getPhoneNumber());
                stmt.setString(6, dto.getDateOfBirth());

                stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Cannot create account!");
            System.out.println(" -- "+e.getMessage());
        }
    }

    public static Account getAccountByLogin(String username,String password) throws ClassNotFoundException, SQLException {
        Connection conn = DatabaseUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(GET_ACCOUNT_BY_LOGIN_QUERY);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        Account account = null;
        while (rs.next()){
            if(rs.getString("password").equals(password)){
                account = setAccount(rs);
            }
        }
        return account;
    }

    public static List<Account> getAccountNotInFriendshipById(int accountId){
        List<Account> accountList = new ArrayList<>();
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt;

            stmt = conn.prepareStatement(GET_ACCOUNT_NOT_IN_FRIENDSHIP_WITH_ID_QUERY);
            stmt.setInt(1, accountId);
            stmt.setInt(2, accountId);
            stmt.setInt(3, accountId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getNString("name"));
                account.setIntroduction(rs.getNString("introduction"));
                account.setAvatarName(rs.getString("avatarName"));
                accountList.add(account);
            }
        }

        catch (Exception e){
            System.out.println("Cannot get people who are not friend!");
            System.out.println(" -- "+e);
        }
        return accountList;
    }

    public static List<Account> getAccountInFriendshipById(int accountId){
        List<Account> accountList = new ArrayList<>();
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt;


            stmt = conn.prepareStatement(GET_ACCOUNT_IN_FRIENDSHIP_WITH_ID_QUERY);
            stmt.setInt(1,accountId);
            stmt.setInt(2, accountId);
            stmt.setInt(3, accountId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getNString("name"));
                account.setIntroduction(rs.getNString("introduction"));
                account.setAvatarName(rs.getString("avatarName"));
                accountList.add(account);
            }
        }

        catch (Exception e){
            System.out.println("Cannot get friend list in relationship!");
            System.out.println(" -- "+e);
        }
        return accountList;
    }
    public static List<Account> getFriendRequest(int accountId){
        List<Account> accountList = new ArrayList<>();
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt;
            stmt = conn.prepareStatement(GET_ACCOUNTS_SEND_FRIEND_REQUEST_QUERY);
            stmt.setInt(1,accountId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getNString("name"));
                account.setIntroduction(rs.getNString("introduction"));
                account.setAvatarName(rs.getString("avatarName"));
                accountList.add(account);
            }
        }
        catch (Exception e){
            System.out.println("Cannot get friend request!");
            System.out.println(" -- "+e);
        }
        return accountList;
    }
    public static List<Account> getMyFriendRequest(int accountId){
        List<Account> accountList = new ArrayList<>();
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt;
            stmt = conn.prepareStatement(GET_ACCOUNTS_GET_FRIEND_REQUEST_QUERY);
            stmt.setInt(1,accountId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getNString("name"));
                account.setIntroduction(rs.getNString("introduction"));
                account.setAvatarName(rs.getString("avatarName"));
                accountList.add(account);
            }
        }
        catch (Exception e){
            System.out.println("Cannot get friend request!");
            System.out.println(" -- "+e);
        }
        return accountList;
    }
    public static List<Account> getGroupMember(int groupId){
        List<Account> accountList = new ArrayList<>();
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt;
            stmt = conn.prepareStatement(GET_GROUP_MEMBER_ACCOUNTS_QUERY);
            stmt.setInt(1,groupId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getNString("name"));
                account.setIntroduction(rs.getNString("introduction"));
                account.setAvatarName(rs.getString("avatarName"));
                accountList.add(account);
            }
        }
        catch (Exception e){
            System.out.println("Cannot get group member!");
            System.out.println(" -- "+e);
        }
        return accountList;
    }

    public static boolean updateAccount(Account account){
        try{
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_ACCOUNT_QUERY);
            stmt.setString(1,account.getUsername());
            stmt.setNString(2, account.getName());
            stmt.setString(3, account.getEmail());
            stmt.setString(4, account.getPhoneNumber());
            stmt.setString(5, account.getDateOfBirth());
            stmt.setNString(6,account.getIntroduction());
            stmt.setString(7,account.getInterest());
            stmt.setString(8,account.getAvatarName());

            stmt.setInt(9, account.getId());


            stmt.executeUpdate();
            return false;
        }
        catch (Exception e){
            System.out.println("Cannot update account!");
            System.out.println(" -- " + e);
        }

        return true;
    }

    public static boolean updatePassword(int accountId,String password) {
        try{
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_PASSWORD_QUERY);
            stmt.setString(1,password);
            stmt.setInt(2, accountId);

            stmt.executeUpdate();

        }catch (Exception e){
            System.out.println("Cannot update password!");
            System.out.println(" -- "+e);
            return false;
        }

        return true;
    }
}
