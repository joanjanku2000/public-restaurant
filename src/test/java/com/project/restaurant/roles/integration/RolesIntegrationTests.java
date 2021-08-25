package com.project.restaurant.roles.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.restaurant.components.roles.dtos.RoleCreateRequest;
import com.project.restaurant.utils.Role;
import com.project.restaurant.utils.Security;
import com.project.restaurant.utils.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RolesIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${roleId}")
    private Long roleId;

    @Test
    @DisplayName("Saving is OK - regular")
    public void test1_saveAndExpect_200() throws Exception {
        RoleCreateRequest roleCreateRequest = new RoleCreateRequest();
        roleCreateRequest.setRole("roles"); //keep it different
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post(Role.SAVE)
                                .accept(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(roleCreateRequest))
                ).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Find by id 200")
    public void test2_findById() throws Exception {
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(Role.FIND)
                        .param("id", String.valueOf(roleId))
                        .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Save: fail - already exists")
    public void test3_saveAndExpect_400() throws Exception {
        RoleCreateRequest roleCreateRequest = new RoleCreateRequest();
        roleCreateRequest.setRole("user");
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(Role.SAVE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
                        .content(objectMapper.writeValueAsString(roleCreateRequest)))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    //    @Test
//    @DisplayName("Delete: success")
//    public void test4_delete() throws Exception {
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.delete(Role.DELETE)
//                        .param("id", String.valueOf(roleId)))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
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
