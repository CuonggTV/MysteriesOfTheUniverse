package com.cuongtv.mysteriesoftheuniverse.controller.Group;

import com.cuongtv.mysteriesoftheuniverse.dao.GroupDao;
import com.cuongtv.mysteriesoftheuniverse.dto.GroupDto;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.entities.Group;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "create-group",urlPatterns = "/create-group")
public class CreateGroupController  extends HttpServlet {
    Account account = new Account();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GroupDto dto = new GroupDto();
        account = CookieUtils.getAccountByCookie(req,resp);

        dto.setGroupName(req.getParameter("groupName"));
        dto.setApprove(req.getParameter("approve"));
        dto.setDetails(req.getParameter("details"));
        List<ValidationError> errors = dto.validate();
        if(errors.isEmpty()){
            Group group = new Group();
            group.setAccountOwner(account.getId());
            group.setName(dto.getGroupName());
            group.setDetails(dto.getDetails());

            if ("yes".equals(dto.getApprove())){
                group.setApprove(true);
            }
            else group.setApprove(false);

            if (GroupDao.createGroup(group)){
                req.setAttribute("success","Create group success!");
            }

        }
        else {
            req.setAttribute("errors",errors);
        }
        req.getRequestDispatcher("/JSP/Group/CreateGroup.jsp").forward(req,resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
         account = CookieUtils.getAccountByCookie(req,resp);

        session.setAttribute("approve","");
        session.setAttribute("groupName","");
        session.setAttribute("details","");
         req.getRequestDispatcher("/JSP/Group/CreateGroup.jsp").forward(req,resp);
    }
}
