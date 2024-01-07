package com.cuongtv.mysteriesoftheuniverse.controller.Post;

import com.cuongtv.mysteriesoftheuniverse.dao.PostDao;
import com.cuongtv.mysteriesoftheuniverse.dao.PostLikeDao;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.entities.Post;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "post",urlPatterns = "/post")
public class PostController extends HttpServlet {
    Account account = new Account();
    private void likeFunction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int postId = Integer.parseInt(req.getParameter("postId"));
        PostLikeDao.updatePostLike(postId,account.getId());

        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
    private void commentFunction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int postId = Integer.parseInt(req.getParameter("postId"));
        Post post = PostDao.getPostById(postId);
        post.setClickComment(true);

        req.getRequestDispatcher("index.jsp").forward(req,resp);

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        account = CookieUtils.getAccountByCookie(req,resp);

        String action = req.getParameter("action");
        System.out.println(action);
        if("like".equals(action)){
            likeFunction(req,resp);
        }
        else if ("comment".equals(action)){
            commentFunction(req,resp);
        }

    }
}
