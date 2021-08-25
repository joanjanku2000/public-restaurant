package com.project.restaurant.items.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.restaurant.base.utils.FieldValuePair;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.ingredients.dtos.IngredientDtoList;
import com.project.restaurant.components.items.dtos.ItemCreateRequest;
import com.project.restaurant.components.items.dtos.ItemUpdateRequest;
import com.project.restaurant.utils.Item;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemIntegrationTests {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${itemId}")
    private int itemId;

    @Value("${ingredientIdToDelete}")
    private int ingredientIdToDelete;

    @Test
    public void test1_save() throws Exception {

        Long[] ingredients = {1L, 2L};
        ItemCreateRequest itemCreateRequest1 = new ItemCreateRequest();
        itemCreateRequest1.setName("Item");
        itemCreateRequest1.setDescription("Description");
        itemCreateRequest1.setPrice(BigDecimal.TEN);
        itemCreateRequest1.setIngredientIds(Arrays.asList(ingredients));

        File f = new File("C:\\Users\\jjanku\\Downloads\\1234.jpg");
        FileInputStream fi1 = new FileInputStream(f);
        MockMultipartFile picture = new MockMultipartFile("picture", f.getName(), "multipart/form-data", fi1);
        MockMultipartFile itemCreateRequest =
                new MockMultipartFile("itemCreateRequest", "", "application/json", objectMapper.writeValueAsString(itemCreateRequest1).getBytes());

        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        log.info("Access Token got : {} ", accessToken);
        try {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.multipart(Item.SAVE)
                                    .file(picture)
                                    .file(itemCreateRequest)
                                    .header(HttpHeaders.AUTHORIZATION,
                                            Security.BEARER + accessToken)

                    ).andExpect(status().isOk())
                    .andReturn();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2_findById_200() throws Exception {
        log.debug("Test should succeed, " +
                "return should be 200 because provided username and passwords correspond to a user ");
        String accessToken = getAccessToken(User.USER_USERNAME, User.PASSWORD);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(Item.FIND)
                                .param("id", String.valueOf(itemId))
                                .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void test3_findItemsOfSeller_200() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(Item.FIND_ITEMS_OF_SELLER, 1))
                .andExpect(status().isOk());
    }

    @Test
    public void test4_updateItem_200() throws Exception {
        ItemUpdateRequest itemUpdateRequest = new ItemUpdateRequest();
        itemUpdateRequest.setName("Update item ");
        itemUpdateRequest.setDescription("Updated Description");
        itemUpdateRequest.setPrice(BigDecimal.ONE);

        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);

        this.mockMvc.perform(MockMvcRequestBuilders.put(Item.UPDATE, itemId)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemUpdateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void test5_addIngredients() throws Exception {
        IngredientDtoList ingredientDtoList = new IngredientDtoList();
        List<Long> ingredientIds = new ArrayList<>();
        ingredientIds.add(1L);
        ingredientIds.add(2L);
        ingredientDtoList.setIngredientIds(ingredientIds);
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.put(Item.ADD_INGREDIENTS, itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredientDtoList)))
                .andExpect(status().isOk());
    }

    @Test
    public void test6_addIngredients_400() throws Exception {
        IngredientDtoList ingredientDtoList = new IngredientDtoList();
        List<Long> ingredientIds = new ArrayList<>();
        ingredientIds.add(205L);
        //ingredientIds.add(2L);
        ingredientDtoList.setIngredientIds(ingredientIds);

        this.mockMvc.perform(MockMvcRequestBuilders.put(Item.ADD_INGREDIENTS, itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredientDtoList)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void test7_deleteIngredient() throws Exception {
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.delete(Item.DELETE_INGREDIENT, itemId, ingredientIdToDelete)
                        .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken))

                .andExpect(status().isOk());
    }

    @Test
    public void test8_addIngredient_200() throws Exception {
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.patch(Item.ADD_INGREDIENT)
                        .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
                        .param("itemId", String.valueOf(itemId))
                        .param("ingredientId", String.valueOf(ingredientIdToDelete)))
                .andExpect(status().isOk());
    }

    // should work when a seller logs in
    @Test
    public void test9_itemsOfLoggedUser() throws Exception {
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc.perform(MockMvcRequestBuilders.get(Item.GET_ITEMS_OF_SELLER_$LOGGED)
                        .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void test10_filter() throws Exception {
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);

        List<FieldValuePair> fieldValuePairList = new ArrayList<>();
        FieldValuePair fieldValuePair = new FieldValuePair();
        fieldValuePair.setField("price");
        fieldValuePair.setValue("13");
        fieldValuePair.setOperation("greaterThanOrEqualTo");
        fieldValuePairList.add(fieldValuePair);

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setFieldValuePair(fieldValuePairList);
        searchCriteria.setOrderBy("price");
        searchCriteria.setSortDirection("asc");

        this.mockMvc.perform(MockMvcRequestBuilders.get(Item.ITEM_FILTER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
                .content(objectMapper.writeValueAsString(searchCriteria))
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
