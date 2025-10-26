package com.gary.springsecurity.controller;

import com.gary.springsecurity.dao.MemberDao;
import com.gary.springsecurity.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
