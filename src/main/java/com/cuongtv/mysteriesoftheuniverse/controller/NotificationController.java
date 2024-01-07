package com.cuongtv.mysteriesoftheuniverse.controller;

import com.cuongtv.mysteriesoftheuniverse.dao.NotificationDao;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.entities.Notification;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="notification", urlPatterns = "/notification")
public class NotificationController extends HttpServlet {
    private Account account = new Account();
    private List<Notification> notificationList = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        account = CookieUtils.getAccountByCookie(req,resp);
        //Remove
        int index = Integer.parseInt(req.getParameter("index"));
        Notification notification = notificationList.get(index);

        //REMOVE SUCCESS
        if(NotificationDao.removeNotification(account.getId(),notification)){
            req.setAttribute("removeSuccess",true);
            notificationList.remove(index);
            session.setAttribute("notificationList",notificationList);

        }

        req.getRequestDispatcher("/JSP/Notification.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        account = CookieUtils.getAccountByCookie(req,resp);
        notificationList = NotificationDao.getNotificationByAccountId(account.getId());

        session.setAttribute("notificationList",notificationList);
        req.getRequestDispatcher("/JSP/Notification.jsp").forward(req,resp);

    }
}
