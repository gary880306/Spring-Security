package com.gary.springsecurity.dao;

import com.gary.springsecurity.model.Member;
import com.gary.springsecurity.model.Role;

import java.util.List;

public interface MemberDao {

    Member getMemberById(Integer memberId);

    Member getMemberByEmail(String email);

    Integer createMember(Member member);

    List<Role> getRolesByMemberId(Integer memberId);
}
