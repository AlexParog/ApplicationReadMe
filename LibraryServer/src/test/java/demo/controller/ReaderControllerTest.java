package demo.controller;

import demo.repository.RepositoryReader;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReaderControllerTest {

    @Autowired
    public MockMvc mvc;
    @Autowired
    public RepositoryReader repositoryReader;

    @AfterEach
    void deleteReader() {
        try{
            repositoryReader.delete(repositoryReader.findReaderEntityByTelephone("+79187770077"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void registration() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName", "Alex");
            jsonObject.put("lastName", "Bobik");
            jsonObject.put("telephone", "+79187770077");
            jsonObject.put("address", "Moscow");
            mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/reader")
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
    void findReader() {
        try {
            String telephone = "+79161110011";
            mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/reader/telephone=" + telephone))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(repositoryReader.findReaderEntityByTelephone(telephone).getId(), jsonObject.getLong("id"));
                        assertEquals(repositoryReader.findReaderEntityByTelephone(telephone).getFirstName(), jsonObject.getString("firstName"));
                        assertEquals(repositoryReader.findReaderEntityByTelephone(telephone).getLastName(), jsonObject.getString("lastName"));
                        assertEquals(repositoryReader.findReaderEntityByTelephone(telephone).getTelephone(), jsonObject.getString("telephone"));
                        assertEquals(repositoryReader.findReaderEntityByTelephone(telephone).getAddress(), jsonObject.getString("address"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}