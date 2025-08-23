package com.gary.springsecurity.service;

import com.gary.springsecurity.dao.MemberDao;
import com.gary.springsecurity.model.Member;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {

  @Autowired private MemberDao memberDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 從 db 查詢 member 數據
    Member member = memberDao.getMemberByEmail(username);
    if (member == null) {
      throw new UsernameNotFoundException("查無用戶: " + username);
    } else {
      String email = member.getEmail();
      String password = member.getPassword();
      List<GrantedAuthority> authorities = new ArrayList<>(); // 權限相關
      return new User(email, password, authorities);
    }
  }
}
