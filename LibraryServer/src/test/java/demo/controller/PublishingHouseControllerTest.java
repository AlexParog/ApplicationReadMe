package demo.controller;

import demo.repository.RepositoryPublishingHouse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PublishingHouseControllerTest {

    @Autowired
    public MockMvc mvc;

    @Autowired
    public RepositoryPublishingHouse repositoryPublishingHouse;

    @Test
    void getAllHouses() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/publishing_houses/AllPublishingHouses"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            assertEquals(repositoryPublishingHouse.findPublishingHouseById(jsonObject.getLong("id")).getId(),jsonObject.getLong("id"));
                            assertEquals(repositoryPublishingHouse.findPublishingHouseById(jsonObject.getLong("id")).getName(),jsonObject.getString("name"));
                            assertEquals(repositoryPublishingHouse.findPublishingHouseById(jsonObject.getLong("id")).getCity(),jsonObject.getString("city"));
                        }
                        assertEquals(jsonArray.length(), repositoryPublishingHouse.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}