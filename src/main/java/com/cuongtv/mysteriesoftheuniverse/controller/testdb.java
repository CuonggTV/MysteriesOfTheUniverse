package com.cuongtv.mysteriesoftheuniverse.controller;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.utils.DatabaseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/test")
public class testdb extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Account> accountList = null;
        try {
            accountList = AccountDao.getAllAccounts();
            for(Account account:accountList){
                System.out.println(account);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
