package site.ancronik.movie.trailer.searcher.info.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import site.ancronik.movie.trailer.searcher.core.domain.entity.ErrorInfo;
import site.ancronik.movie.trailer.searcher.core.presentation.controllers.ApplicationErrorController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ApplicationErrorController.class)
public class ApplicationErrorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void returnJsonDefaultError_TestConfiguredProperly_ReturnErrorInfo() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/error").accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        ErrorInfo errorInfo = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(response.getContentAsString(), ErrorInfo.class);

        Assertions.assertEquals("General error", errorInfo.getErrorMsg());
    }

}
