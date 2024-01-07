package com.cuongtv.mysteriesoftheuniverse.controller.Profile;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.dto.ChangePasswordDto;
import com.cuongtv.mysteriesoftheuniverse.dto.ProfileDto;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "change-password",urlPatterns = "/change-password")
public class ChangePasswordController extends HttpServlet {
    Account account = new Account();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setOldPass(account.getPassword());
        dto.setInputOldPass(req.getParameter("oldPass"));
        dto.setNewPass(req.getParameter("newPass"));
        dto.setConfirmPass(req.getParameter("confirmPass"));

        List<ValidationError> errors = dto.validate();
        if(errors.isEmpty()) {
            boolean updateStatus = false;

            updateStatus = AccountDao.updatePassword(account.getId(),dto.getNewPass());
            if (updateStatus){
                account.setPassword(dto.getNewPass());
            }

            req.setAttribute("updateStatus", updateStatus);
            req.setAttribute("account", account);
            req.getRequestDispatcher("/JSP/Profile/ChangePassword.jsp").forward(req, resp);

        }
        else {
            req.setAttribute("account",account);
            req.setAttribute("errors",errors);
            req.getRequestDispatcher("/JSP/Profile/ChangePassword.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       account = CookieUtils.getAccountByCookie(req,resp);

        req.setAttribute("account",account);
        req.getRequestDispatcher("/JSP/Profile/ChangePassword.jsp").forward(req,resp);
    }
}
