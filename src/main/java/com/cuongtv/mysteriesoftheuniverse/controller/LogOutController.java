package com.cuongtv.mysteriesoftheuniverse.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet (urlPatterns = "/Logout")
public class LogOutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        for(Cookie cookie:cookies){
            if (cookie.getName().equals("accountID")){
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
                break;
            }
        }
        resp.sendRedirect("Login");
    }
}
