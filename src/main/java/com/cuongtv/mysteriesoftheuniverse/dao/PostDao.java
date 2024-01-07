package com.cuongtv.mysteriesoftheuniverse.dao;

import com.cuongtv.mysteriesoftheuniverse.entities.Post;
import com.cuongtv.mysteriesoftheuniverse.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDao {
    private static String GET_HOME_POST = "SELECT p.id,a.avatarName, a.name AS username,g.name AS groupName,p.accountId, p.details,p.timeSent,p.imageName, p.visibility\n" +
            "FROM dbo.Post AS p\n" +
            "JOIN dbo.Account AS a ON a.id = p.accountId\n" +
            "LEFT JOIN dbo.[Group] AS g ON g.groupId = p.groupId\n" +
            "-- Private cua minh\n" +
            "   WHERE p.accountId = ?\n" +
            "-- pUBLIC \n" +
            "   OR p.visibility = 'public'\n" +
            "-- GROUP DA JOIN\n" +
            "   OR (P.visibility = 'group' \n" +
            "       AND ? IN (\n" +
            "           SELECT accountMember FROM dbo.GroupMember\n" +
            "           WHERE groupId = g.groupId\n" +
            "       )\n" +
            "   )";
    private static String GET_POST_BY_ID_ACCOUNT_QUERY = "SELECT p.id,a.avatarName, a.name AS username,g.name AS groupName,p.accountId, p.details,p.timeSent,p.imageName,p.visibility\n" +
            "FROM dbo.Post AS p\n" +
            "JOIN dbo.Account AS a ON a.id = p.accountId\n" +
            "LEFT JOIN dbo.[Group] AS g ON g.groupId = p.groupId\n"+
            "WHERE p.accountId = ? AND p.visibility != 'group';";
    private static String GET_POST_BY_GROUP_ID_QUERY = "SELECT p.id,a.avatarName, a.name AS username,g.name AS groupName,p.accountId, p.details,p.timeSent,p.imageName,p.visibility\n" +
            "FROM dbo.Post AS p \n" +
            "JOIN dbo.Account AS a ON a.id = p.accountId\n" +
            "JOIN dbo.[Group] AS g ON g.groupId = p.groupId\n" +
            "WHERE g.groupId = ? AND p.groupAccepted = ?;";

    private static String GET_POST_BY_ID_QUERY = "SELECT p.id,a.avatarName, a.name AS username,g.name AS groupName,p.accountId, p.details,p.timeSent,p.imageName,p.visibility\n" +
            "FROM dbo.Post AS p " +
            "JOIN dbo.Account AS a ON a.id = p.accountId " +
            "LEFT JOIN dbo.[Group] AS g ON g.groupId = p.groupId \n" +
            "WHERE p.id = ?";

    private static String GET_FAVORITE_POST_QUERY = "SELECT p.id,a.avatarName, a.name AS username,g.name AS groupName,p.accountId, p.details,p.timeSent,p.imageName,p.visibility\n" +
            "FROM dbo.Favorite AS F\n" +
            "JOIN dbo.Post AS p ON p.id = f.postId\n" +
            "JOIN dbo.Account AS a ON a.id = p.accountId\n" +
            "LEFT JOIN dbo.[Group] AS g ON g.groupId = p.groupId\n" +
            "WHERE f.accountId = ?;";

    private static String GET_PROFILE_POST_QUERY = "SELECT p.id,a.avatarName, a.name AS username,g.name AS groupName,p.accountId, p.details,p.timeSent,p.imageName, p.visibility\n" +
            "FROM dbo.Post AS p\n" +
            "JOIN dbo.Account AS a ON a.id = p.accountId\n" +
            "LEFT JOIN dbo.[Group] AS g ON g.groupId = p.groupId\n" +
            "WHERE p.accountId = ?\n" +
            "AND p.visibility = 'public'";
    private static String UPDATE_GROUP_ACCEPTED_QUERY = "UPDATE dbo.Post\n" +
            "SET groupAccepted = 1\n" +
            "WHERE id = ?;";
    private static String UPDATE_POST_QUERY = "UPDATE dbo.Post\n" +
            "SET details = ?, visibility = ?\n" +
            "WHERE id = ?;";
    private static String INSERT_POST_QUERY = "INSERT INTO [Post] ([accountId], [groupId],[visibility], [timeSent], [details], [imageName])\n" +
            "VALUES\n" +
            "  (?, ?, ?,GETDATE(),?, ?);";

    private static Post setPost(ResultSet resultSet) throws SQLException {
        Post post = new Post(resultSet.getInt("id"));
        post.setUsername(resultSet.getNString("username"));
        post.setAccountId(resultSet.getInt("accountId"));
        post.setGroupName(resultSet.getString("groupName"));
        post.setDetails(resultSet.getNString("details"));
        post.setTimeSent(resultSet.getString("timeSent"));
        post.setImageName(resultSet.getString("imageName"));
        post.setVisibility(resultSet.getString("visibility"));
        post.setAvatarName(resultSet.getString("avatarName"));
        post.setCommentList(PostCommentDao.getCommentsByPostId(post.getId()));
        return post;
    }

    public static List<Post> getHomePost(int accountId){
        List<Post> postList = new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_HOME_POST);
            statement.setInt(1,accountId);
            statement.setInt(2,accountId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Post post = setPost(resultSet);
                postList.add(post);
            }
        }catch (Exception e){
            System.out.println("Cannot get home post!");
            System.out.println(" -- "+e);
        }

        return postList;
    }

    public static List<Post> getOwnPost(int id){
        List<Post> postList = new ArrayList<>();
        try{

            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_POST_BY_ID_ACCOUNT_QUERY);
            statement.setString(1,String.valueOf(id));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Post post = setPost(resultSet);
                postList.add(post);
            }

        }
        catch (Exception e){
            System.out.println("Cannot get own post!");
            System.out.println(" -- "+ e);
        }
        return postList;
    }
    public static List<Post> getProfilePost(int accountId){
        List<Post> postList = new ArrayList<>();
        try{

            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_PROFILE_POST_QUERY);
            statement.setInt(1,accountId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Post post = setPost(resultSet);
                postList.add(post);
            }

        }
        catch (Exception e){
            System.out.println("Cannot get profile post!");
            System.out.println(" -- "+ e);
        }
        return postList;
    }

    public static List<Post> getFavoritePost(int accountId){
        List<Post> postList = new ArrayList<>();
        try{

            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_FAVORITE_POST_QUERY);
            statement.setInt(1,accountId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Post post = setPost(resultSet);
                postList.add(post);
            }

        }
        catch (Exception e){
            System.out.println("Cannot get profile post!");
            System.out.println(" -- "+ e);
        }
        return postList;
    }



    public static Post getPostById(int postId){
        Post post = new Post();
        try {

            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_POST_BY_ID_QUERY);
            statement.setString(1,String.valueOf(postId));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                post = setPost(resultSet);
            }
        }
        catch (Exception e){
            System.out.println("Cannot get post by id!");
            System.out.println(" -- "+e);
        }
        return post;
    }

    public static List<Post> getPostByGroupId(int groupId,int groupAccept){
        List<Post> postList = new ArrayList<>();
        try{
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_POST_BY_GROUP_ID_QUERY);
            statement.setInt(1,groupId);
            statement.setInt(2,groupAccept);


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Post post = setPost(resultSet);
                postList.add(post);
            }
        }
        catch (Exception e){
            System.out.println("Cannot get post by group id!");
            System.out.println(" -- "+e);
        }

        return postList;
    }

    public static void createPost(Post post){
        try{
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_POST_QUERY);

            statement.setInt(1,post.getAccountId());
            statement.setInt(2,post.getGroupId());
            statement.setString(3,post.getVisibility());
            statement.setNString(4, post.getDetails());
            statement.setString(5,post.getImageName());

            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("Cannot create post!");
            System.out.println(" -- "+e);
        }

    }

    public static void updateGroupAccepted(int postId){
        try{
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_GROUP_ACCEPTED_QUERY);

            statement.setInt(1,postId);
            statement.executeUpdate();

        }catch (Exception e){
            System.out.println("Cannot update post!");
            System.out.println(" -- "+e);
        }
    }

    public static boolean updatePost(int postId,String visibility,String details){
        try{
            Connection connection = DatabaseUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_POST_QUERY);


            statement.setNString(1,details);
            statement.setString(2,visibility);
            statement.setInt(3,postId);

            statement.executeUpdate();
            return true;
        }catch (Exception e){
            System.out.println("Cannot update post!");
            System.out.println(" -- "+e);
        }
        return false;
    }



}
