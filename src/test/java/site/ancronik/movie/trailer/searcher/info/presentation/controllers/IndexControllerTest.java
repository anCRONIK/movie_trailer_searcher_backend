package site.ancronik.movie.trailer.searcher.info.presentation.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getIndexText_TestConfiguredProperly_ReturnTextFromMessagesProperties() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertEquals("Thank you for using Movie Trailer Searcher", response.getContentAsString());
    }

    @Test
    public void getIndexText_TestConfiguredProperly_ReturnOk() throws Exception {
        MockHttpServletResponse response = mvc.perform(head("/").accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    public void getIndexText_PostMethodInsteadOfGet_ReturnMethodNotAllowed() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("/").accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatus());
    }

}
