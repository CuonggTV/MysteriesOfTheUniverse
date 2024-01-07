package com.cuongtv.mysteriesoftheuniverse.controller.Chat;

import com.cuongtv.mysteriesoftheuniverse.dao.GroupDao;
import com.cuongtv.mysteriesoftheuniverse.dao.MessageDao;
import com.cuongtv.mysteriesoftheuniverse.dto.MessageDto;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.entities.Group;
import com.cuongtv.mysteriesoftheuniverse.entities.Message;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
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

@WebServlet(name = "group-chat",urlPatterns = "/group-chat")
public class GroupChatController extends HttpServlet {
    private Account account = new Account();
    private Group group =new Group();
    private List<Group> groupList = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private void findFriend(HttpServletRequest req,HttpServletResponse resp) {
        String search = req.getParameter("chatSearch");
        List<Group> groupSearch = new ArrayList<>(groupList);
        HttpSession session = req.getSession();

        if (search.length() == 0){
            session.setAttribute("chatList",groupSearch);
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
            session.setAttribute("chatList", groupSearch);
        }
    }

    private void sendMessage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        account = CookieUtils.getAccountByCookie(req,resp);
        HttpSession session = req.getSession();
//      SEND MESSAGE
        MessageDto messageDto = new MessageDto(req.getParameter("message"));

        List<ValidationError> errors = messageDto.validate();
        if (errors.isEmpty()) {
            Message message = new Message();

            message.setAccountSent(account.getId());
            message.setAccountReceived(0);
            message.setGroupId(group.getId());
            message.setDetails(req.getParameter("message"));

            MessageDao.createMessage(message);

            messages.add(message);
            session.setAttribute("messages",messages);
        }
        else {
            req.setAttribute("errors",errors);
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("sendMessage".equals(action)){
            sendMessage(req,resp);
        }
        else if ("chatSearch".equals(action)){
            findFriend(req,resp);
        }

        req.getRequestDispatcher("/JSP/Chat.jsp").forward(req,resp);

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        account = CookieUtils.getAccountByCookie(req,resp);
        HttpSession session = req.getSession();
        groupList = GroupDao.getJoinGroup(account.getId());


        if (req.getParameter("id") != null){
            group = GroupDao.getGroupById(req.getParameter("id"));
        }

        else {
            if(!groupList.isEmpty()){
                group = groupList.get(0);
            }
        }

        messages = MessageDao.getMessageByGroupId(account.getId(),group.getId());
        req.setAttribute("directLink","group-chat");

        session.setAttribute("group",true);
        session.setAttribute("chatObject",group);
        session.setAttribute("account",account);
        session.setAttribute("chatList",groupList);
        session.setAttribute("messages",messages);
        req.getRequestDispatcher("/JSP/Chat.jsp").forward(req,resp);
    }
}
