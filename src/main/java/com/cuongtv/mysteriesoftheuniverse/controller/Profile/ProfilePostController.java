package com.cuongtv.mysteriesoftheuniverse.controller.Profile;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
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


@WebServlet(name = "profile-post",urlPatterns = "/profile-post")
public class ProfilePostController extends HttpServlet {
    private List<Post> postList = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostUtils.function(req,resp,postList,"/JSP/Profile/ProfilePost.jsp");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession();

        Account accountProfile = AccountDao.getAccountById(id);
        Account account = CookieUtils.getAccountByCookie(req,resp);

        if (account.getId() == accountProfile.getId()){
            postList = PostDao.getOwnPost(id);
        }
        else {
            postList = PostDao.getProfilePost(id);
        }
        PostUtils.setFunctionForPost(account,postList);

        session.setAttribute("account",account);
        session.setAttribute("accountProfile",accountProfile);
        session.setAttribute("postList",postList);
        req.getRequestDispatcher("/JSP/Profile/ProfilePost.jsp").forward(req,resp);
    }
}
