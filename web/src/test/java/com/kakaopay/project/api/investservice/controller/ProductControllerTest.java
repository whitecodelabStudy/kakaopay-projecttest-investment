package com.kakaopay.project.api.investservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void helloTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/product")).andExpect(MockMvcResultMatchers.status().isOk());
  }

}
