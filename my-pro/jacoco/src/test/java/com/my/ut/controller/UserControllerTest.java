package com.my.ut.controller;

import com.my.ut.entity.User;
import com.my.ut.mapper.UserMapper;
import com.my.ut.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserMapper userMapper;
    @SpyBean
    private UserService userService;
    @Resource
    private UserController userController;

    @Resource
    private MockMvc mockMvc;

    @Test
    public void testSelectUserById() throws Exception {
        User user = new User();
        user.setId(1001);
        user.setName("test01");
        Mockito.when(userMapper.selectUserById(Mockito.anyInt())).thenReturn(user);
//        String result = userController.selectUserById(1002);
        /* 模拟一次get请求*/
        RequestBuilder rb = MockMvcRequestBuilders.get("/selectUser")
                .contentType(MediaType.TEXT_HTML_VALUE)
                .param("id","123456");
        MvcResult result = mockMvc.perform(rb).andReturn();
        Assertions.assertEquals(user.toString(),result.getResponse().getContentAsString());
    }

}
