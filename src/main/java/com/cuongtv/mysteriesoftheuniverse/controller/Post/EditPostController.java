package com.cuongtv.mysteriesoftheuniverse.controller.Post;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.dao.PostDao;
import com.cuongtv.mysteriesoftheuniverse.entities.Post;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
import com.cuongtv.mysteriesoftheuniverse.utils.FileUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "edit-post",urlPatterns = "/edit-post")

public class EditPostController extends HttpServlet {
    private Post post = new Post();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String visibility = req.getParameter("visibility");
        String details = req.getParameter("details");
        HttpSession session = req.getSession();

        List<ValidationError> errors = new ArrayList<>();
        if (details.length() == 0){
            errors.add(new ValidationError("details","Details must not be empty!",details));
        }
        else if(details.length()>1000){
            errors.add(new ValidationError("details","Details must not pass 1000 letters!",details));
        }

        if (errors.isEmpty()){
            req.setAttribute("success",PostDao.updatePost(post.getId(), visibility,details));
            session.setAttribute("visibility",visibility);
            session.setAttribute("details",details);
        }
        else {
            req.setAttribute("errors",errors);
        }
        req.getRequestDispatcher("/JSP/EditPost.jsp").forward(req,resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        post = (Post) session.getAttribute("post");

            session.setAttribute("groupName",post.getGroupName());
            session.setAttribute("visibility",post.getVisibility());
            session.setAttribute("details",post.getDetails());

            req.getRequestDispatcher("/JSP/EditPost.jsp").forward(req,resp);


    }
}
