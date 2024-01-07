package com.cuongtv.mysteriesoftheuniverse.controller.Post;

import com.cuongtv.mysteriesoftheuniverse.dao.GroupDao;
import com.cuongtv.mysteriesoftheuniverse.dao.PostDao;
import com.cuongtv.mysteriesoftheuniverse.dto.PostDto;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.entities.Group;
import com.cuongtv.mysteriesoftheuniverse.entities.Post;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import com.cuongtv.mysteriesoftheuniverse.utils.FileUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MultipartConfig()
@WebServlet(name="create-post",urlPatterns = "/create-post")
public class CreatePostController extends HttpServlet {
    Account account = new Account();
    List<Group> groupList = new ArrayList<>();
    private void keepOldInput(HttpServletRequest req,HttpSession session){
        String visibility = req.getParameter("visibility");
        String details = req.getParameter("details");

        session.setAttribute("visibility",visibility);
        session.setAttribute("details",details);
    }

    private void updateRadioBox(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession();
        String visibility = req.getParameter("visibility");

        if("group".equals(visibility)){
            session.setAttribute("isGroup",true);
            session.setAttribute("groupList",groupList);
        }
        else {
            session.setAttribute("isGroup",false);
        }
        keepOldInput(req,session);
    }
    private void findGroups(HttpServletRequest req,HttpServletResponse resp) {
        String groupSearch = req.getParameter("groupSearch");
        List<Group> findGroup = groupList;

        HttpSession session = req.getSession();
        if (groupSearch.equals("")) {
            session.setAttribute("groupList", groupList);
        } else {
            Pattern pattern = Pattern.compile(groupSearch, Pattern.CASE_INSENSITIVE);

            int i = 0;
            while (i < findGroup.size()) {
                Matcher matcher = pattern.matcher(findGroup.get(i).getName());
                if (!matcher.find()) {
                    findGroup.remove(i);
                } else {
                    i++;
                }
            }
            session.setAttribute("groupList", findGroup);
        }
        keepOldInput(req,session);

    }
    private void submitAll(HttpServletRequest req,HttpServletResponse resp) {
        PostDto dto = new PostDto();

        dto.setVisibility(req.getParameter("visibility"));

        if (req.getParameter("groupId")!=null){
            dto.setGroupId(Integer.parseInt(req.getParameter("groupId")));
        }
        else {
            dto.setGroupId(0);
        }
        dto.setDetails(req.getParameter("details"));
        dto.setImageName(FileUtils.downloadUploadFile(req));

        List<ValidationError> errors = dto.validate();
        if (errors.isEmpty()){
            Post post = new Post();
            post.setAccountId(account.getId());
            post.setGroupId(dto.getGroupId());
            post.setVisibility(dto.getVisibility());
            post.setDetails(dto.getDetails());
            post.setImageName(dto.getImageName());
            if (PostDao.createPost(post)){
                req.setAttribute("success",true);
            }
        }
        else {
            req.setAttribute("errors",errors);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        account = CookieUtils.getAccountByCookie(req,resp);
        groupList = GroupDao.getJoinGroup(account.getId());
        String action = req.getParameter("action");

        if("updateRadioBox".equals(action) ){
            updateRadioBox(req,resp);
        }
        else if ("findGroups".equals(action)){
            findGroups(req,resp);
        }
        else if("submitAll".equals(action)){
            submitAll(req,resp);
        }
        req.getRequestDispatcher("/JSP/CreatePost.jsp").forward(req,resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session =req.getSession();
        req.setAttribute("visibility","");
        session.setAttribute("details","");
        account = CookieUtils.getAccountByCookie(req,resp);
        req.getRequestDispatcher("/JSP/CreatePost.jsp").forward(req,resp);
    }
}
