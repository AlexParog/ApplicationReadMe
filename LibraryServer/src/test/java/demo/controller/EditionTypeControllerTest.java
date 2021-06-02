package demo.controller;

import demo.repository.RepositoryEditionType;
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
class EditionTypeControllerTest {

    @Autowired
    public MockMvc mvc;

    @Autowired
    public RepositoryEditionType repositoryEditionType;

    @Test
    void getAllHouses() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/editions/AllEditions"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            assertEquals(repositoryEditionType.findEditionTypeById(jsonObject.getLong("id")).getId(),jsonObject.getLong("id"));
                            assertEquals(repositoryEditionType.findEditionTypeById(jsonObject.getLong("id")).getTypeName(),jsonObject.getString("typeName"));
                        }
                        assertEquals(jsonArray.length(), repositoryEditionType.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}