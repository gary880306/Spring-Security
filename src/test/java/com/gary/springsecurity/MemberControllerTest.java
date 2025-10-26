package com.gary.springsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gary.springsecurity.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    // 認證(Authenticate)相關

    @Test
    public void register() throws Exception {
        Member member = new Member();
        member.setEmail("test1@gmail.com");
        member.setPassword("111");
        member.setName("test1");
        member.setAge(26);
        String json = objectMapper.writeValueAsString(member);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .header("content-type", "application/json")
                .content(json);
        mockMvc.perform(request).andExpect(status().is(200));

        RequestBuilder loginRequest = MockMvcRequestBuilders
                .post("/userLogin")
                .with(httpBasic("test1@gmail.com", "111"));
        mockMvc.perform(loginRequest).andExpect(status().is(200));
    }

    @Test
    public void userLogin() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/userLogin")
                .with(httpBasic("normal@gmail.com", "normal"));
        mockMvc.perform(request).andExpect(status().is(200));
    }

}
