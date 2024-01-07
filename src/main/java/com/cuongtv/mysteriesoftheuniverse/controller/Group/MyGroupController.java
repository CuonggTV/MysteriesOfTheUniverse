package com.cuongtv.mysteriesoftheuniverse.controller.Group;

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

@WebServlet(name = "my-group",urlPatterns = "/my-group")
public class MyGroupController extends HttpServlet {
    private Account account = new Account();
    private List<Group> groupList = new ArrayList<>();

    private void findGroup(HttpServletRequest req,HttpServletResponse resp) {
        String search = req.getParameter("groupSearch");
        List<Group> groupSearch = new ArrayList<>(groupList);
        HttpSession session = req.getSession();
        if (search.length() == 0){
            session.setAttribute("groupList",groupList);
        }
        else {
            Pattern pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);

            int i = 0;
            while (i < groupSearch.size()) {
                Matcher matcher = pattern.matcher(groupSearch.get(i).getName());
                if (!matcher.find()) {
                    groupSearch.remove(i);
                } else {
                    i++;
                }
            }
            session.setAttribute("groupList",groupSearch);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action =req.getParameter("action");
        int index = -1;
        int groupId = 0;

        if (req.getParameter("index")!=null){
            index = Integer.parseInt(req.getParameter("index"));
            groupId = groupList.get(index).getId();
        }

        if("join".equals(action)){
            GroupMemberDao.insertGroupMember(groupId,account.getId());
            groupList.remove(index);
        }
        else if ("leave".equals(action)) {
            System.out.println("groupId: "+groupId +", account.id" + account.getId());
            GroupMemberDao.removeGroupMember(groupId,account.getId());
            groupList.remove(index);
        }

        if ("groupSearch".equals(action)) {
            findGroup(req, resp);
        }
        else{
            req.setAttribute("groupList",groupList);
        }

        if ("edit".equals(action)){
            HttpSession session = req.getSession();
            session.setAttribute("group",groupList.get(index));
            resp.sendRedirect("edit-group");
        }
        else {
            req.getRequestDispatcher("/JSP/Group/MyGroup.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        account = CookieUtils.getAccountByCookie(req,resp);
        String action = req.getParameter("pageAction");
        String title;

        if("findGroup".equals(action)){
            groupList = GroupDao.getNotJoinGroup(account.getId());
            title = "Find Group";
        }
        else if("seeJoinedGroup".equals(action)){
            groupList = GroupDao.getSeeJoinGroup(account.getId());
            title = "See Joined Group";
        } else if ("createGroup".equals(action)) {
            title = "Create Group";
        } else {
            groupList = GroupDao.getGroupByAccountId(account.getId());
            title = "My Group";
        }

        if ("createGroup".equals(action)){
            resp.sendRedirect("create-group");
        }
        else{
            session.setAttribute("title",title);
            req.setAttribute("account",account);
            session.setAttribute("groupList",groupList);
            req.getRequestDispatcher("/JSP/Group/MyGroup.jsp").forward(req,resp);
        }


    }
}
