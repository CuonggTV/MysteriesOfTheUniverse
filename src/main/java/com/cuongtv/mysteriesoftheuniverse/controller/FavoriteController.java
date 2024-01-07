package com.cuongtv.mysteriesoftheuniverse.controller;

import com.cuongtv.mysteriesoftheuniverse.dao.PostDao;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.entities.Post;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import com.cuongtv.mysteriesoftheuniverse.utils.PostUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "favorite",urlPatterns = "/favorite")
public class FavoriteController extends HttpServlet {
    private List<Post> postList = new ArrayList<>();
    private Account account = new Account();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostUtils.function(req,resp,postList,"/JSP/Favorite.jsp");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        account = CookieUtils.getAccountByCookie(req,resp);
        postList = PostDao.getFavoritePost(account.getId());

        PostUtils.setFunctionForPost(account,postList);

        session.setAttribute("account",account);
        session.setAttribute("postList",postList);
        req.getRequestDispatcher("/JSP/Favorite.jsp").forward(req,resp);

    }
}
