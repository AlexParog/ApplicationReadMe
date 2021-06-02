package demo.controller;

import demo.repository.RepositoryAuthor;
import demo.repository.RepositoryBook;
import demo.repository.RepositoryEditionType;
import demo.repository.RepositoryPublishingHouse;
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
class BooksControllerTest {

    @Autowired
    public MockMvc mvc;

    @Autowired
    public RepositoryBook repositoryBook;

    @Autowired
    public RepositoryAuthor repositoryAuthor;

    @Autowired
    public RepositoryEditionType repositoryEditionType;

    @Autowired
    public RepositoryPublishingHouse repositoryPublishingHouse;

    @Test
    void getAllBooks() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/books/AllBooks"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            assertEquals(repositoryBook.findBooksById(jsonObject.getLong("id")).getId(), jsonObject.getLong("id"));
                            assertEquals(repositoryBook.findBooksById(jsonObject.getLong("id")).getNameOfTheBook(), jsonObject.getString("nameOfTheBook"));
                            assertEquals(repositoryBook.findBooksById(jsonObject.getLong("id")).getYearOfIssue(), LocalDate.parse(jsonObject.getString("yearOfIssue")));
                        }
                        assertEquals(jsonArray.length(), repositoryBook.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteBook() {
        try {
            long id = 1;
            mvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/books/id=" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void createBook() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nameOfTheBook", "Книга");
            jsonObject.put("yearOfIssue", "1500-05-02");
            jsonObject.put("author", 2);
            jsonObject.put("editionType", 3);
            jsonObject.put("publishingHouse", 2);
            mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/books//addBook")
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
    void update() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", "2");
            jsonObject.put("nameOfTheBook", "\"Как писать курсовую работу за час?\"");
            jsonObject.put("yearOfIssue", "1999-05-02");
            jsonObject.put("author", 2);
            jsonObject.put("editionType", 3);
            jsonObject.put("publishingHouse", 2);
            mvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/books/update")
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