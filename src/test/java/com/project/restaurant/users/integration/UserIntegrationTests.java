package com.project.restaurant.users.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.restaurant.components.user.dtos.UserCreateRequest;
import com.project.restaurant.components.user.dtos.UserUpdateRequest;
import com.project.restaurant.utils.Security;
import com.project.restaurant.utils.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserIntegrationTests {

    private final String email = User.EMAIL_TO_REGISTER;
    private final String password = User.PASSWORD_TO_REGISTER;
    private final int uid = User.ID;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test1_saveUser_200() throws Exception {
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setEmail(email);
        userCreateRequest.setPassword(password);
        userCreateRequest.setFullname("FullName");
        userCreateRequest.setPhoneNumber("087312");
        userCreateRequest.setAddress("Address");
        userCreateRequest.setCityId(1L);
        userCreateRequest.setRoleId(2L);

        this.mockMvc.perform(MockMvcRequestBuilders.post(User.REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void test2_findUser_200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(User.FIND_USER)
                        .param("id", String.valueOf(uid)))
                .andExpect(status().isOk());

    }

    @Test
    public void test3_updateUser_200() throws Exception {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setPhoneNumber("123456789");

        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.put(User.UPDATE_USER)
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequest))
                        .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void test4_findAllUsers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(User.FIND_ALL_USERS))
                .andExpect(status().isOk());
    }

    @Test
    public void test5_showALlSellers_200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(User.SHOW_SELLERS)).andExpect(status().isOk());
    }

    private String getAccessToken(String username, String password) throws Exception {
        log.info("Username is {}", username);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post(User.LOGIN)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("username", username)
                        .param("password", password))
                .andReturn();

        log.info("Got result {}", result.getResponse().getContentAsString());
        if (result.getResponse().getStatus() == 200) {
            String fullBearer =
                    result.getResponse().getContentAsString()
                            .substring(0, result.getResponse()
                                    .getContentAsString().indexOf("refresh"));
            String accessToken = fullBearer.substring("access_token:".length() + 3)
                    .replace(",", "")
                    .replace("\"", "");

            log.warn("Got access token {}", accessToken);
            return accessToken;
        }

        return "";
    }
}
