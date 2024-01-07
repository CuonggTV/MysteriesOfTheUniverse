package com.cuongtv.mysteriesoftheuniverse.controller.Group;

import com.cuongtv.mysteriesoftheuniverse.dao.GroupDao;
import com.cuongtv.mysteriesoftheuniverse.dao.PostDao;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.entities.Group;
import com.cuongtv.mysteriesoftheuniverse.entities.Post;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import com.cuongtv.mysteriesoftheuniverse.utils.PostUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "group",urlPatterns = "/group")
public class GroupController extends HttpServlet {
    private List<Post> postList = new ArrayList<>();
    private Account account = new Account();
    private Group group = new Group();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostUtils.function(req,resp,postList,"/JSP/Group/Group.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        account = CookieUtils.getAccountByCookie(req,resp);
        group = GroupDao.getGroupById(req.getParameter("id"));
        postList = PostDao.getPostByGroupId(group.getId(),1);

        PostUtils.setFunctionForPost(account,postList);

        session.setAttribute("account",account);
        session.setAttribute("group",group);
        session.setAttribute("postList",postList);
        req.getRequestDispatcher("/JSP/Group/Group.jsp").forward(req,resp);

    }
}
