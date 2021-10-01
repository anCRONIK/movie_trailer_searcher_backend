package site.ancronik.movie.trailer.searcher.info.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import site.ancronik.movie.trailer.searcher.info.domain.entities.InfoResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(InfoController.class)
public class InfoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getIndexText_TestConfiguredProperly_ReturnTextFromMessagesProperties() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());

        InfoResponse infoResponse = new ObjectMapper().readValue(response.getContentAsString(), InfoResponse.class);

        Pattern pattern = Pattern.compile("\\d\\.\\d\\.\\d", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(infoResponse.getApplicationVersion());

        Assertions.assertTrue(matcher.matches());

        Assertions.assertEquals("Thank you for using Movie Trailer Searcher. For any question contact 'ancronik@gmail.com'", infoResponse.getInfoMessage());
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
