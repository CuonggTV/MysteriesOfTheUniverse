package com.cuongtv.mysteriesoftheuniverse.controller.Profile;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.dto.ProfileDto;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import com.cuongtv.mysteriesoftheuniverse.utils.FileUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig()
@WebServlet(name = "edit-profile",urlPatterns = "/edit-profile")
public class EditProfileController extends HttpServlet {
    private Account account = new Account();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        account = CookieUtils.getAccountByCookie(req,resp);

        String avatarName = FileUtils.downloadAvatar(req,account.getAvatarName());
        System.out.println(avatarName);

        ProfileDto dto = new ProfileDto();
        dto.setId(account.getId());
        dto.setUsername(req.getParameter("username"));

        dto.setCurrentUsername(account.getUsername());
        dto.setCurrentEmail(account.getEmail());

        dto.setName(req.getParameter("name"));
        dto.setEmail(req.getParameter("email"));
        dto.setPhoneNumber(req.getParameter("phone"));
        dto.setDateOfBirth(req.getParameter("dob"));
        dto.setIntroduction(req.getParameter("intro"));
        dto.setInterest(req.getParameter("interest"));

        List<ValidationError> errors = dto.validate();

        if(avatarName == null || avatarName.length() == 0){
            avatarName = account.getAvatarName();
        }
        else if("File size error!".equals(avatarName)){
            errors.add(new ValidationError("avatar","Avatar must not pass 5MB!",avatarName));
        }
        else if ("File image error!".equals(avatarName)){
            errors.add(new ValidationError("avatar","Avatar must be jpg or png!",avatarName));
        }

        if(errors.isEmpty()) {
            account.setUsername(dto.getUsername());
            account.setName(dto.getName());
            account.setEmail(dto.getEmail());
            account.setPhoneNumber(dto.getPhoneNumber());
            account.setDateOfBirth(dto.getDateOfBirth());
            account.setIntroduction(dto.getIntroduction());
            account.setInterest(dto.getInterest());
            account.setAvatarName(avatarName);

            boolean updateStatus = AccountDao.updateAccount(account);
            req.setAttribute("updateStatus", updateStatus);
        }
        else {
            req.setAttribute("errors",errors);
        }

        req.setAttribute("account", account);
        req.getRequestDispatcher("/JSP/Profile/EditProfile.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        account = CookieUtils.getAccountByCookie(req,resp);
        req.setAttribute("account",account);
        req.getRequestDispatcher("/JSP/Profile/EditProfile.jsp").forward(req,resp);
    }
}
