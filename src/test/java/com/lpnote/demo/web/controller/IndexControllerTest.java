package com.lpnote.demo.web.controller;

import com.lpnote.demo.TestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class IndexControllerTest extends TestBase {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testIndex() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}
