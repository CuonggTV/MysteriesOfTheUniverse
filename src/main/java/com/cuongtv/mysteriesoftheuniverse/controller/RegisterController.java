package com.cuongtv.mysteriesoftheuniverse.controller;


import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.dto.RegisterDto;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Register",urlPatterns = "/Register")
public class RegisterController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegisterDto dto = new RegisterDto();
        dto.setUsername(req.getParameter("username"));
        dto.setPassword(req.getParameter("password"));
        dto.setConfirmPassword(req.getParameter("confirmPassword"));
        dto.setName(req.getParameter("name"));
        dto.setEmail(req.getParameter("email"));
        dto.setPhoneNumber(req.getParameter("phoneNumber"));
        dto.setDateOfBirth(req.getParameter("dob"));

        List<ValidationError> errors = dto.validate();
        if (!errors.isEmpty()){
            req.setAttribute("errors",errors);
            req.getRequestDispatcher("/JSP/Register.jsp").forward(req,resp);
        }
        else{
            AccountDao.createAccount(dto);
            resp.sendRedirect("Login");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/JSP/Register.jsp").forward(req,resp);
    }
}
