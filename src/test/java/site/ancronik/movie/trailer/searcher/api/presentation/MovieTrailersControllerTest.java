package site.ancronik.movie.trailer.searcher.api.presentation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;
import site.ancronik.movie.trailer.searcher.api.domain.services.MovieTrailerSearchService;
import site.ancronik.movie.trailer.searcher.api.presentation.controllers.MovieTrailersController;
import site.ancronik.movie.trailer.searcher.config.TestConfiguration;
import site.ancronik.movie.trailer.searcher.core.exception.DataServiceException;
import site.ancronik.movie.trailer.searcher.util.JsonResourceObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = { TestConfiguration.class, MovieTrailersController.class })
public class MovieTrailersControllerTest {

    @Autowired
    JsonResourceObjectMapper jsonResourceObjectMapper;

    @MockBean
    MovieTrailerSearchService movieTrailerSearchServiceMock;

    @Autowired
    private MockMvc mvc;

    @Test
    public void searchMovieTrailersByTitle_MakeCall_StatusOk() throws Exception {
        List<MovieTrailerSearchResponse> responseData =
            (List<MovieTrailerSearchResponse>) jsonResourceObjectMapper.loadTestJson("api_responses/search-movie-trailers-response.json", List.class);

        Mockito.when(movieTrailerSearchServiceMock.searchMovieTrailersForTitle(Mockito.any())).thenReturn(responseData);

        ResultActions requestResult = mvc.perform(get("/api/movie-trailers").param("title", "sparta")
            .accept(MediaType.APPLICATION_JSON));
        requestResult.andExpect(status().isOk());

        Mockito.verify(movieTrailerSearchServiceMock).searchMovieTrailersForTitle(Mockito.any());
        Mockito.verifyNoMoreInteractions(movieTrailerSearchServiceMock);
    }

    @Test
    public void searchMovieTrailersByTitle_MakeCall_ServiceThrowsError() {
        Mockito.when(movieTrailerSearchServiceMock.searchMovieTrailersForTitle(Mockito.any())).thenThrow(DataServiceException.class);

        Assertions.assertThrows(NestedServletException.class, () -> mvc.perform(get("/api/movie-trailers").param("title", "sparta")
            .accept(MediaType.APPLICATION_JSON)));

        Mockito.verify(movieTrailerSearchServiceMock).searchMovieTrailersForTitle(Mockito.any());
        Mockito.verifyNoMoreInteractions(movieTrailerSearchServiceMock);
    }

}
