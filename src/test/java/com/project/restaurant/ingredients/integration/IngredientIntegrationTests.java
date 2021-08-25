package com.project.restaurant.ingredients.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.restaurant.components.ingredients.dtos.IngredientCreateRequest;
import com.project.restaurant.utils.Ingredient;
import com.project.restaurant.utils.Security;
import com.project.restaurant.utils.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureMockMvc
public class IngredientIntegrationTests {

    private final String name = "Ingredient223"; //++
    private final double calories = 20.1;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Value(
            "${ingredientId}"
    )
    private long id;

    @Test
    @DisplayName("Save Test : Success")
    public void test1_saveAndExpect_200() throws Exception {
        IngredientCreateRequest ingredientCreateRequest
                = new IngredientCreateRequest(name, calories);
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(Ingredient.SAVE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
                        .content(objectMapper.writeValueAsString(ingredientCreateRequest)))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @DisplayName(
            "Find by id : ingredient"
    )
    public void test2_findById() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get(Ingredient.FIND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("id", String.valueOf(id)))
                .andExpect(status().isOk()
                ).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("Find by name : ingredient")
    public void test3_findByName() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(Ingredient.FIND_BY_NAME, name)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("Save fail - exists")
    public void test4_saveAndExpect_400() throws Exception {
        IngredientCreateRequest ingredientCreateRequest
                = new IngredientCreateRequest(name, calories);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(Ingredient.SAVE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredientCreateRequest)))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("Delete ingredient Bad Request,items have it")
    public void test5_delete_400() throws Exception {
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(Ingredient.DELETE)
                        .param("id", String.valueOf(id))
                        .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken))
                .andExpect(status().isBadRequest());
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
