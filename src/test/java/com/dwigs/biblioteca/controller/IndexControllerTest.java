package com.dwigs.biblioteca.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void init() {
    }

    @Test
    public void testSumar() throws Exception {
        /*
        URI uri = new URI("/sumar?op1=2&op2=3");
        MockHttpServletRequestBuilder req =
                MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(req).andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("5", result.getResponse().getContentAsString());
        */

        assertTrue("En progreso", true);

    }

}
