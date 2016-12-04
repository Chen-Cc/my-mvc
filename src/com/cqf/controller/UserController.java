package com.cqf.controller;

import com.cqf.annotation.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 78544 on 2016/11/24.
 */
public class UserController {

    @Path("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "/view/login.jsp";
    }
}
