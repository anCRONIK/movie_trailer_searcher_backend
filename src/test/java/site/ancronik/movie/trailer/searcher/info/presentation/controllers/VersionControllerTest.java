package site.ancronik.movie.trailer.searcher.info.presentation.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(VersionController.class)
public class VersionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void geVersion_TestConfiguredProperly_ReturnValidVersion() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/version").accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Pattern pattern = Pattern.compile("\\d\\.\\d\\.\\d", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(response.getContentAsString());

        Assertions.assertTrue(matcher.matches());
    }

}
