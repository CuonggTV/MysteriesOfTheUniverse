package com.cuongtv.mysteriesoftheuniverse.controller.Friend;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.dao.FriendshipDao;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import com.cuongtv.mysteriesoftheuniverse.entities.Friendship;
import com.cuongtv.mysteriesoftheuniverse.entities.Group;
import com.cuongtv.mysteriesoftheuniverse.utils.CookieUtils;
import jakarta.el.ELClass;
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

@WebServlet(name = "friend",urlPatterns = "/friend")
public class FriendController extends HttpServlet {
    private Account account = new Account();
    List<Account> friendList = new ArrayList<>();

    private void findFriend(HttpServletRequest req,HttpServletResponse resp) {
        String search = req.getParameter("friendSearch");
        List<Account> friendSearch = new ArrayList<>(friendList);
        HttpSession session = req.getSession();

        if (search.length() == 0){
            session.setAttribute("friendList",friendList);
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
            session.setAttribute("friendList", friendSearch);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int index = -1;
        int friendId = 0;

        try {
            index = Integer.parseInt(req.getParameter("index"));
            friendId = friendList.get(index).getId();
        }catch (Exception e){
            System.out.println("Cannot get index and friend id!");
            System.out.println(" -- "+e);
        }

        String action = req.getParameter("action");
        if (index != -1 && friendId != 0){
            if("deleteFriendship".equals(action)){
                FriendshipDao.deleteFriendship(account.getId(),friendId);
            }
            else if ("acceptRequest".equals(action)){
                FriendshipDao.updateFriendship(account.getId(),friendId,true);
            }
            else if ("refuseRequest".equals(action)){
                FriendshipDao.deleteFriendship(account.getId(),friendId);
            }
            else if ("makeFriend".equals(action)){
                FriendshipDao.sendFriendRequest(friendId,account.getId());
            }
            friendList.remove(index);
            session.setAttribute("friendList",friendList);
        }

        if ("friendSearch".equals(action)){
            findFriend(req,resp);
        }


        req.getRequestDispatcher("/JSP/Friend/Friend.jsp").forward(req,resp);
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        account = CookieUtils.getAccountByCookie(req,resp);
        HttpSession session = req.getSession();

        String pageAction = req.getParameter("pageAction");
        String title ;

        if ("friendRequest".equals(pageAction)){
            friendList = AccountDao.getFriendRequest(account.getId());
            title = "Friend Request";
        }
        else if("myFriendRequest".equals(pageAction)){
            friendList = AccountDao.getMyFriendRequest(account.getId());
            title = "My Friend Request";
        }
        else if ("findFriend".equals(pageAction)){
            friendList =AccountDao.getAccountNotInFriendshipById(account.getId());
            title = "Find Friend";
        }
        // Case: pageAction == myFriend
        else {
            friendList = AccountDao.getAccountInFriendshipById(account.getId());
            title = "My friend";
        }


        session.setAttribute("title",title);
        session.setAttribute("friendList",friendList);
        req.setAttribute("account",account);
        req.getRequestDispatcher("/JSP/Friend/Friend.jsp").forward(req,resp);
    }
}
