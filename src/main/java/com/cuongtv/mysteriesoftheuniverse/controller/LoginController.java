package com.cuongtv.mysteriesoftheuniverse.controller;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.dto.LoginDto;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Login",urlPatterns = "/Login")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginDto dto = new LoginDto();
        dto.setUsername(req.getParameter("username"));
        dto.setPassword(req.getParameter("password"));
        Account account = null;
        try {
            account = AccountDao.getAccountByLogin(dto.getUsername(), dto.getPassword());
        } catch (Exception e) {
            System.out.println("Error "+ e);
        }

        if(account!=null){
            Cookie cookie = new Cookie("accountID",String.valueOf(account.getId()));
            resp.addCookie(cookie);
            resp.sendRedirect("home");
        }
        else{
            List<ValidationError> errors = dto.validate();
            req.setAttribute("errors",errors);
            req.getRequestDispatcher("/JSP/Login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/JSP/Login.jsp").forward(req,resp);
    }
}
