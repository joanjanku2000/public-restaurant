package com.project.restaurant.orders.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.restaurant.utils.Order;
import com.project.restaurant.utils.Security;
import com.project.restaurant.utils.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
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
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderIntegrationTests {
    private final String DESCRIPTION = "Description";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${item_Id_1}")
    private int itemId;
    @Value("${order_id}")
    private int orderId; // remember to orderId++

    @Test
    public void test1_save_200() throws Exception {
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.post(Order.PLACE_ORDER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
                .param("itemId", String.valueOf(itemId))
        ).andExpect(status().isOk());
    }

    @Test
    public void test2_find_200() throws Exception {
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.get(Order.FIND_ORDER)
                .param("id", String.valueOf(orderId))
                .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
        ).andExpect(status().isOk());
    }

    @Test
    public void test3_find_400() throws Exception {
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.get(Order.FIND_ORDER)
                .param("id", String.valueOf(-4))
                .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    public void test4_feedback_400() throws Exception {
        log.info("Order not delivered");
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.put(Order.FEEDBACK, orderId, DESCRIPTION)
                .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    public void test5_deliver_200() throws Exception {
        log.info("Deliver order");
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.put(Order.DELIVER, orderId)
                .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
        ).andExpect(status().isOk());
    }

    @Test
    public void test6_feedback_200() throws Exception {
        log.info("Order delivered, feedback allowed");
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.put(Order.FEEDBACK, orderId, DESCRIPTION)
                .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
        ).andExpect(status().isOk());
    }

    @Test
    public void test7_myOrders_200() throws Exception {
        log.info("My orders test");
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.get(Order.MY_ORDERS)
                .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
        ).andExpect(status().isOk());
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
