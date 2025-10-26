package com.gary.springsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MyTest {

    @Autowired
    private MockMvc mockMvc;

    // Authentication & Authorization

//    @Test
//    public void testHello() throws Exception {
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/hello")
//                .header("Authorization", "Basic dGVzdDFAZ21haWwuY29tOjExMQ==");
//        mockMvc.perform(request).andExpect(status().is(200));
//    }

    @Test
    public void testHello() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/hello")
                .with(httpBasic("test1@gmail.com", "111")); // 簡化.header("Authorization", "Basic XXX");
        mockMvc.perform(request).andExpect(status().is(200));
    }

    @Test
    public void testAuthorization() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/authorization")
                .with(httpBasic("test2@gmail.com", "222"));
        mockMvc.perform(request).andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "mock", roles = {"NORMAL_MEMBER"})
    public void testAuthorizationWithMock() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/authorization");
        mockMvc.perform(request).andExpect(status().is(403));
    }

    @Test
    public void testAuthorizationWithADM() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/authorization")
                .with(httpBasic("test1@gmail.com", "111"));
        mockMvc.perform(request).andExpect(status().is(200));
    }

}
