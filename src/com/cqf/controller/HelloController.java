package com.cqf.controller;

import com.cqf.annotation.Param;
import com.cqf.annotation.Path;
import com.cqf.annotation.View;
import com.cqf.model.Model;
import com.cqf.model.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Created by 78544 on 2016/11/24.
 */

public class HelloController {

    @Path("/hello")
    public String sayHello(@Param("words") String words, @Param(value = "id") int id, UserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("id=" + entity.getId());
        System.out.println("userName=" + entity.getUserName());
        System.out.println("age=" + request.getParameter("age"));
        return "hello";
    }

    @Path("/speaking")
    public String speaking(@Param("words") String words, @Param("num") Integer num, @Param("f") float f) {
        System.out.println("num = " + num);
        System.out.println("speaking = " + words);
        return words;
    }


    @Path("/login")
    @View("/view/login.jsp")
    public Model login(@Param("userName") String userName, @Param("password") String password){
        Model model = new Model();
        model.setAttribute("userName", userName);
        model.setAttribute("password", password);
        return model;
    }
}
