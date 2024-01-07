package com.cuongtv.mysteriesoftheuniverse.utils;

import com.cuongtv.mysteriesoftheuniverse.dao.FavoriteDao;
import com.cuongtv.mysteriesoftheuniverse.dao.PostCommentDao;
import com.cuongtv.mysteriesoftheuniverse.dao.PostLikeDao;
import com.cuongtv.mysteriesoftheuniverse.dto.MessageDto;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.entities.Comment;
import com.cuongtv.mysteriesoftheuniverse.entities.Post;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PostUtils {
    public static void setFunctionForPost(Account account, List<Post> postList){
        for (Post post: postList){
            post.setLiked(PostLikeDao.checkIsLikedOnPostId(post.getId(),account.getId()));
            post.setFavorite(FavoriteDao.checkFavorite(post.getId(),account.getId()));
        }
        Collections.reverse(postList);
    }

    private static void likeFunction(HttpServletRequest req, List<Post> postList, Account account){
        int index = Integer.parseInt(req.getParameter("index"));
        Post post = postList.get(index);
        PostLikeDao.updatePostLike(post.getId(),account.getId());

        if(post.getIsLiked()){
            postList.get(index).setLiked(false);
        }
        else postList.get(index).setLiked(true);
    }
    private static void sendCommentFunction(HttpServletRequest req, List<Post> postList, Account account) {
        HttpSession session = req.getSession();
        int index = Integer.parseInt(req.getParameter("index"));

        MessageDto messageDto = new MessageDto(req.getParameter("comment"));

        List<ValidationError> errors = messageDto.validate();
        if (errors.isEmpty()){
            Comment comment = new Comment();
            comment.setAccountId(account.getId());
            comment.setAccountName(account.getName());
            comment.setPostId(Integer.parseInt(req.getParameter("postId")));
            comment.setDetails(messageDto.getMessage());

            comment.setAvatarPath(account.getAvatarName());

            PostCommentDao.createComment(comment);

            postList.get(index).getCommentList().add(comment);
            session.setAttribute("postList",postList);
        }
        else {
            req.setAttribute("errors",errors);
        }


    }

    public static void function(HttpServletRequest req, HttpServletResponse resp, List<Post> postList,String dispatcherFile) throws IOException, ServletException {
        Account account = CookieUtils.getAccountByCookie(req,resp);
        HttpSession session = req.getSession();
        int index = Integer.parseInt(req.getParameter("index"));
        String action = req.getParameter("action");


        if("like".equals(action)){
            likeFunction(req,postList,account);
            session.setAttribute("postList",postList);
        }
        else if ("comment".equals(action)){
            postList.get(index).setClickComment(true);
            session.setAttribute("postList",postList);
        }
        else if("sendComment".equals(action)){
            sendCommentFunction(req,postList,account);
        }
        else if("setFavorite".equals(action)){
            FavoriteDao.createFavorite(postList.get(index).getId(),account.getId());
            postList.get(index).setFavorite(true);
        }
        else if("removeFavorite".equals(action)){
            FavoriteDao.deleteFavorite(postList.get(index).getId(),account.getId());
            postList.get(index).setFavorite(false);
        }


        if ("editPost".equals(action)) {
            session.setAttribute("post",postList.get(index));
            resp.sendRedirect("edit-post");
        }
        else {
            req.getRequestDispatcher(dispatcherFile).forward(req,resp);
        }
    }


}
