package com.gary.springsecurity.controller;

import com.gary.springsecurity.dao.MemberDao;
import com.gary.springsecurity.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class MemberController {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody Member member) {
        // 省略參數檢查

        // hash 原始密碼
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // db插入一筆新的 Member 數據
        Integer memberId = memberDao.createMember(member);
        return "註冊成功";
    }

    @PostMapping("/userLogin")
    public String userLogin(Authentication authentication) {
        // 取得使用者帳號
        String userName = authentication.getName();
        // 取得始御者權限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return "登入成功！帳號： " + userName + " 的權限為： " + authorities;
    }

}
