package com.cuongtv.mysteriesoftheuniverse.controller;

import com.cuongtv.mysteriesoftheuniverse.dao.*;
import com.cuongtv.mysteriesoftheuniverse.entities.*;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import com.cuongtv.mysteriesoftheuniverse.utils.PostUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Home",urlPatterns = "/home")
public class HomeController extends HttpServlet {
    private Account account = null;
    private List<Account> friendList = new ArrayList<>();
    private List<Group> groupList = new ArrayList<>();
    private List<Post> postList = new ArrayList<>();
    private List<Notification> notificationList = new ArrayList<>();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostUtils.function(req,resp,postList,"index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        account = CookieUtils.getAccountByCookie(req,resp);

            HttpSession session = req.getSession();


            postList = PostDao.getHomePost(account.getId());


            groupList = GroupDao.getJoinGroup(account.getId());
            friendList = AccountDao.getAccountInFriendshipById(account.getId());
            notificationList = NotificationDao.getNotificationByAccountId(account.getId());

        PostUtils.setFunctionForPost(account,postList);


            session.setAttribute("notificationList",notificationList);
            session.setAttribute("account",account);
            session.setAttribute("postList",postList);
            session.setAttribute("friendList",friendList);
            session.setAttribute("groupList",groupList);
            req.getRequestDispatcher("index.jsp").forward(req,resp);

    }
}
