package demo.controller;

import demo.repository.RepositoryAuthor;
import demo.repository.RepositoryBook;
import demo.utils.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
class AuthorsControllerTest {

    @Autowired
    public MockMvc mvc;

    @Autowired
    public RepositoryAuthor repositoryAuthor;

    @BeforeEach
    void createRemoteAuthor() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName", "Александр");
            jsonObject.put("lastName", "Васильев");
            jsonObject.put("birthday", "2000-12-23");
            jsonObject.put("description", "Автор");
            mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/authors/addAuthor")
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

    @AfterEach
    void deleteCreatedAuthor() {
        try {
            long id = 26;
            repositoryAuthor.delete(repositoryAuthor.findAuthorById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void createAuthor() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName", "Alex");
            jsonObject.put("lastName", "Bublik");
            jsonObject.put("birthday", "1999-05-02");
            jsonObject.put("description", "author");
            mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/authors/addAuthor")
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
    void getAllAuthors() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/authors/AllAuthors"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            assertEquals(repositoryAuthor.findAuthorById(jsonObject.getLong("id")).getId(), jsonObject.getLong("id"));
                            assertEquals(repositoryAuthor.findAuthorById(jsonObject.getLong("id")).getFirstName(), jsonObject.getString("firstName"));
                            assertEquals(repositoryAuthor.findAuthorById(jsonObject.getLong("id")).getLastName(), jsonObject.getString("lastName"));
                            assertEquals(repositoryAuthor.findAuthorById(jsonObject.getLong("id")).getBirthday(), LocalDate.parse(jsonObject.getString("birthday")));
                            assertEquals(repositoryAuthor.findAuthorById(jsonObject.getLong("id")).getDescription(), jsonObject.getString("description"));
                        }
                        assertEquals(jsonArray.length(), repositoryAuthor.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteAuthor() {
        try {
            long id = 1;
            mvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/authors/id=" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void update() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", "2");
            jsonObject.put("firstName", "Alex");
            jsonObject.put("lastName", "Bablik");
            jsonObject.put("birthday", "1999-05-02");
            jsonObject.put("description", "author");
            mvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/authors/update")
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