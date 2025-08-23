package com.gary.springsecurity.dao;

import com.gary.springsecurity.model.Member;

public interface MemberDao {

  Member getMemberById(Integer memberId);

  Member getMemberByEmail(String email);

  Integer createMember(Member member);
}
