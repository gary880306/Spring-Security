package com.gary.springsecurity.controller;

import com.gary.springsecurity.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class MyController {

    @Autowired
    private MemberDao memberDao;

    @GetMapping("/hello")
    public String hello(Authentication authentication) {

        // 取得使用者的帳號
        String username = authentication.getName();

        // 方法層級授權測試
        String name = memberDao.getName();

        // 取得使用者的權限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return "Hello " + username + "! 你的權限為: " + authorities + "方法層級授權測試: " + name;
    }

    @PostMapping("/welcome")
    public String welcome() {
        return "Welcome!";
    }

    @GetMapping("/authorization")
    public String authorization() {
        return "200OK";
    }

    @PostMapping("/userLogin")
    public String userLogin() {
        return "登入";
    }
}
