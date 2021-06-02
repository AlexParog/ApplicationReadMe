package demo.controller;

import demo.repository.RepositoryCoWorkers;
import demo.repository.RepositoryExemplar;
import demo.repository.RepositoryIssueOfBook;
import demo.repository.RepositoryReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookIssueControllerTest {

    @Autowired
    public MockMvc mvc;

    @Autowired
    public RepositoryIssueOfBook bookIssue;

    @Autowired
    public RepositoryReader repositoryReader;

    @Autowired
    public RepositoryExemplar repositoryExemplar;

    @Autowired
    public RepositoryCoWorkers repositoryCoWorkers;

    @Test
    void getAllIssues() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/book_issue/AllIssues"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            assertEquals(bookIssue.findIssueOfBookByExemplarEntityId(jsonObject.getLong("id")).getId(), jsonObject.getLong("id"));
                            assertEquals(bookIssue.findIssueOfBookByExemplarEntityId(jsonObject.getLong("id")).getInventoryNumber(), jsonObject.getInt("inventoryNumber"));
                            assertEquals(bookIssue.findIssueOfBookByExemplarEntityId(jsonObject.getLong("id")).getReturnDate(), LocalDate.parse(jsonObject.getString("returnDate")));
                            assertEquals(bookIssue.findIssueOfBookByExemplarEntityId(jsonObject.getLong("id")).getLost(), jsonObject.getBoolean("lost"));
                        }
                        assertEquals(jsonArray.length(), bookIssue.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addNewIssue() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("inventoryNumber", 6);
            jsonObject.put("returnDate", "2021-06-15");
            jsonObject.put("lost", false);
            mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/book_issue/new_issue")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonObject.toString())
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void editIssue() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("inventoryNumber", 3);
            jsonObject.put("returnDate", "2021-06-19");
            jsonObject.put("lost", false);
            mvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/book_issue/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonObject.toString())
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}