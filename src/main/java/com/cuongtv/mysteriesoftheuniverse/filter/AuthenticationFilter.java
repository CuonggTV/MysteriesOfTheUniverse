package com.cuongtv.mysteriesoftheuniverse.filter;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter extends HttpFilter {
    public static boolean checkCookie(HttpServletRequest req, HttpServletResponse resp){
        try{
            Cookie[]cookies = req.getCookies();
            for (Cookie cookie: cookies){
                if (cookie.getName().equals("accountID")){
                   return true;
                }
            }
        }
        catch (Exception e){
            System.out.println("Cannot get cookies!");
        }
        return false;
    }
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String path = req.getServletPath();

        if("/Login".equals(path) || "/JSP/Login.jsp".equals(path) || checkCookie(req,res)){
            chain.doFilter(req,res);
        }
        else if (!checkCookie(req,res)){
            res.sendRedirect("Login");
        }
    }
}
