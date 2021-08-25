package com.project.restaurant.city.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.restaurant.base.utils.FieldValuePair;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.cities.dtos.CityCreateRequest;
import com.project.restaurant.utils.City;
import com.project.restaurant.utils.Security;
import com.project.restaurant.utils.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CityIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${cityId}")
    private long id; // remember to id++ in yaml


    @Test
    public void saveCityTest() throws Exception {
        CityCreateRequest cityCreateRequest = new CityCreateRequest();
        cityCreateRequest.setName("City23"); //City++ ;)
        String accessToken = getAccessToken(User.USERNAME, User.PASSWORD);
        this.mockMvc
                .perform(post(City.SAVE)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, Security.BEARER + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cityCreateRequest))
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void findCityTest() throws Exception {
        this.mockMvc
                .perform(get(City.FIND)
                        .param("id", String.valueOf(id))

                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Permet"))
                .andReturn();
    }


    @Test
    public void cityFilter() throws Exception {
        List<FieldValuePair> fieldValuePairList = new ArrayList<>();
        FieldValuePair fieldValuePair = new FieldValuePair();
        fieldValuePair.setField("name");
        fieldValuePair.setValue("City");
        fieldValuePairList.add(fieldValuePair);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setFieldValuePair(fieldValuePairList);

        this.mockMvc
                .perform(get(City.FILTER)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchCriteria)))
                .andExpect(status().isOk())
                .andReturn()
        ;
    }

    @Test
    public void cityFilterNullPassing() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria();
        this.mockMvc
                .perform(get(City.FILTER)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchCriteria)))
                .andExpect(status().isOk())
                .andReturn()
        ;
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
//    @Test
//    public void deleteCityTest_400_peopleLiveThere() throws Exception {
//        this.mockMvc
//                .perform(delete(City.DELETE)
//                        .param("id", String.valueOf(id))
//                )
//                .andExpect(status().isBadRequest())
//                .andReturn();
//    }
}
