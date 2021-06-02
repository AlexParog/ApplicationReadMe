package demo.controller;

import demo.repository.RepositoryCoWorkers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CoWorkerControllerTest {

    @Autowired
    public MockMvc mvc;

    @Autowired
    public RepositoryCoWorkers coWorker;

    @Test
    void registration() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", "bobik");
            jsonObject.put("firstName", "Alex");
            jsonObject.put("lastName", "Bobik");
            jsonObject.put("telephone", "+79187770077");
            jsonObject.put("city", "SPB");
            jsonObject.put("password", "1234");
            mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/worker/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonObject.toString())
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void signIn() {
        long login = 2;
        String password = "1234";
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/worker/signInLogin=" + login + "&Password=" + password))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        assertEquals(String.valueOf(coWorker.findCoWorkersById(login).getPassword().equals(password.hashCode())), body);
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void findWorker() {
        try {
            String login = "admin";
            mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/worker/Login=" + login))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(coWorker.findCoWorkersEntityByLogin(login).getId(), jsonObject.getLong("id"));
                        assertEquals(coWorker.findCoWorkersEntityByLogin(login).getFirstName(), jsonObject.getString("firstName"));
                        assertEquals(coWorker.findCoWorkersEntityByLogin(login).getLastName(), jsonObject.getString("lastName"));
                        assertEquals(coWorker.findCoWorkersEntityByLogin(login).getTelephone(), jsonObject.getString("telephone"));
                        assertEquals(coWorker.findCoWorkersEntityByLogin(login).getCity(), jsonObject.getString("city"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
