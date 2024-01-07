package com.cuongtv.mysteriesoftheuniverse.utils;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.entities.Account;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public class CookieUtils {
    public static Optional<String> getCookieByName(HttpServletRequest req, String name){
        Cookie []cookies = req.getCookies();
        return Stream.of(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

    public static Account getAccountByCookie(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Account account = null;
        try{
            Cookie []cookies = req.getCookies();
            for (Cookie cookie: cookies){
                if (cookie.getName().equals("accountID")){
                    int id =Integer.parseInt(cookie.getValue());
                    try {
                        account = AccountDao.getAccountById(id);
                    } catch (Exception e) {
                        System.out.println("Cannot get account by id!");
                        System.out.println(" -- "+e);
                    }
                    break;
                }
            }
        }
        catch (Exception e){
            System.out.println("Cannot get cookies!");
        }
        return account;
    }
}
