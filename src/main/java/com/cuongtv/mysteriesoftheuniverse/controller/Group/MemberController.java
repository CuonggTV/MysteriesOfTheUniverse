package com.cuongtv.mysteriesoftheuniverse.controller.Group;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.dao.GroupDao;
import com.cuongtv.mysteriesoftheuniverse.dao.GroupMemberDao;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.entities.Group;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "members",urlPatterns = "/members")
public class MemberController extends HttpServlet {
    private Account account = new Account();
    private Group group;

    private void findMember(HttpServletRequest req,HttpServletResponse resp) {
        String search = req.getParameter("memberSearch");
        List<Account> memberSearch = new ArrayList<>(memberList);
        HttpSession session = req.getSession();


        if (search.length() == 0){
            session.setAttribute("memberList",memberList);
        }
        else {
            Pattern pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
            int i = 0;
            while (i < memberSearch.size()) {
                Matcher matcher = pattern.matcher(memberSearch.get(i).getName());
                if (!matcher.find()) {
                    memberSearch.remove(i);
                } else {
                    i++;
                }
            }

            session.setAttribute("memberList", memberSearch);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int index = 0;
        try{
            index = Integer.parseInt(req.getParameter("action"));
        }
        catch (NumberFormatException e){
        }

        if("remove".equals(action)){
            HttpSession session = req.getSession();
            GroupMemberDao.removeGroupMember(group.getId(), memberList.get(index).getId());
            memberList.remove(index);
            session.setAttribute("memberList", memberList);
        }
        else if ("search".equals(action)) {
            findMember(req,resp);
        }

        req.setAttribute("account",account);
        req.setAttribute("group",group);
        req.getRequestDispatcher("/JSP/Group/GroupMember.jsp").forward(req,resp);
    }

    private List<Account> memberList = new ArrayList<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        group = GroupDao.getGroupById(req.getParameter("id"));
        account = CookieUtils.getAccountByCookie(req,resp);
        memberList = AccountDao.getGroupMember(group.getId());

        HttpSession session = req.getSession();


        session.setAttribute("memberList",memberList);
        req.setAttribute("account",account);
        req.setAttribute("group",group);
        req.getRequestDispatcher("/JSP/Group/GroupMember.jsp").forward(req,resp);
    }
}
