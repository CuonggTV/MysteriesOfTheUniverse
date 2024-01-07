package com.cuongtv.mysteriesoftheuniverse.controller.Profile;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "profile",urlPatterns = "/profile")
public class ProfileController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = AccountDao.getAccountById(Integer.parseInt(req.getParameter("id")));
        Account accountCookie= CookieUtils.getAccountByCookie(req,resp);

        req.setAttribute("accountCookie",accountCookie);
        req.setAttribute("account",account);
        req.getRequestDispatcher("/JSP/Profile/Profile.jsp").forward(req,resp);
    }
}
