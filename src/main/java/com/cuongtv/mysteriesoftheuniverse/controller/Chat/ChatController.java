package com.cuongtv.mysteriesoftheuniverse.controller.Chat;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.dao.MessageDao;
import com.cuongtv.mysteriesoftheuniverse.dto.MessageDto;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
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

@WebServlet(name = "chat",urlPatterns = "/chat")
public class ChatController extends HttpServlet {
    private Account account = new Account();
    private Account accountChat =new Account();
    private List<Account> friendList = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();


    private void findFriend(HttpServletRequest req,HttpServletResponse resp) {
        String search = req.getParameter("chatSearch");
        List<Account> friendSearch = new ArrayList<>(friendList);
        HttpSession session = req.getSession();

        if (search.length() == 0){
            session.setAttribute("chatList",friendList);
        }
        else {
            Pattern pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);

            int i = 0;
            while (i < friendSearch.size()) {
                Matcher matcher = pattern.matcher(friendSearch.get(i).getName());
                if (!matcher.find()) {
                    friendSearch.remove(i);
                } else {
                    i++;
                }
            }
            session.setAttribute("chatList", friendSearch);
        }
    }
    private void sendMessage(HttpServletRequest req,HttpServletResponse resp) throws  IOException {
        account = CookieUtils.getAccountByCookie(req,resp);
        HttpSession session = req.getSession();

        MessageDto messageDto = new MessageDto(req.getParameter("message"));

        List<ValidationError> errors = messageDto.validate();
        if (errors.isEmpty()) {
            Message message = new Message();
            message.setAccountSent(account.getId());
            message.setAccountReceived(accountChat.getId());
            message.setGroupId(0);
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


        friendList = AccountDao.getAccountInFriendshipById(account.getId());

        if (req.getParameter("id") != null){
            accountChat = AccountDao.getAccountById(Integer.parseInt(req.getParameter("id")));
        }

        else {
            if(!friendList.isEmpty()){
                accountChat = friendList.get(0);
            }
            else {

            }
        }


        messages = MessageDao.getMessageById(account.getId(),accountChat.getId());


        req.setAttribute("directLink","chat");

        session.setAttribute("chatObject",accountChat);
        session.setAttribute("account",account);
        session.setAttribute("chatList",friendList);
        session.setAttribute("messages",messages);
        req.getRequestDispatcher("/JSP/Chat.jsp").forward(req,resp);
    }
}
