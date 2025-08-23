package com.gary.springsecurity.rowmapper;

import com.gary.springsecurity.model.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberRowMapper implements RowMapper<Member> {

  @Override
  public Member mapRow(ResultSet resultSet, int i) throws SQLException {
    Member member = new Member();
    member.setMemberId(resultSet.getInt("member_id"));
    member.setEmail(resultSet.getString("email"));
    member.setPassword(resultSet.getString("password"));
    member.setName(resultSet.getString("name"));
    member.setAge(resultSet.getInt("age"));

    return member;
  }
}
