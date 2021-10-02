package site.ancronik.movie.trailer.searcher.api.data.drivers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import site.ancronik.movie.trailer.searcher.config.TestConfiguration;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieIdTitleData;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieResponse;
import site.ancronik.movie.trailer.searcher.core.exception.DataServiceException;
import site.ancronik.movie.trailer.searcher.util.ResourceUtil;
import site.ancronik.movie.trailer.searcher.util.WebClientTestUtil;

import java.util.List;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class ImdbApiCallerTest {

    private final WebClient mockWebClient = Mockito.mock(WebClient.class);

    private ImdbApiCaller defaultImdbApiCaller;

    @BeforeEach
    void initImdbApiCaller() {
        defaultImdbApiCaller = new ImdbApiCaller(mockWebClient);
    }

    @Test
    public void checkIfInitialized() {
        Assertions.assertTrue(Objects.nonNull(defaultImdbApiCaller));
    }

    @Test
    public void findAllMovieIdsForTitle_NullGiven_ReturnEmptyList() {
        List<ImdbMovieIdTitleData> data = defaultImdbApiCaller.findAllMovieIdsForTitle(null);

        Assertions.assertNotNull(data);
        Assertions.assertTrue(data.isEmpty());
        Mockito.verifyNoInteractions(mockWebClient);
    }

    @Test
    public void findAllMovieIdsForTitle_EmptyStringGiven_ReturnEmptyList() {
        List<ImdbMovieIdTitleData> data = defaultImdbApiCaller.findAllMovieIdsForTitle("");

        Assertions.assertNotNull(data);
        Assertions.assertTrue(data.isEmpty());
        Mockito.verifyNoInteractions(mockWebClient);
    }

    @Test
    public void findAllMovieIdsForTitle_ValidTitleGiven_ReturnData() {
        WebClient webClient = WebClientTestUtil.buildWebClientForResponseFromResourceFile("imdb_api_responses/getMovieIdByTitle-response.json");

        List<ImdbMovieIdTitleData> data = new ImdbApiCaller(webClient).findAllMovieIdsForTitle("sparta");

        Assertions.assertNotNull(data);
        Assertions.assertFalse(data.isEmpty());
        Assertions.assertEquals(3, data.size());
        Assertions.assertEquals("ImdbMovieIdTitleData(imdbId=tt0054331, title=Spartacus)", data.get(0).toString());
        Assertions.assertEquals("ImdbMovieIdTitleData(imdbId=tt1073498, title=Meet the Spartans)", data.get(1).toString());
        Assertions.assertEquals("ImdbMovieIdTitleData(imdbId=tt0360009, title=Spartan)", data.get(2).toString());
    }

    @Test
    public void findAllMovieIdsForTitle_ValidTitleGiven_ApiResponseIsInternalServerError_ThrowException() {
        WebClient webClient = WebClientTestUtil.buildWebClientForBadResponse(HttpStatus.INTERNAL_SERVER_ERROR);

        Assertions.assertThrows(DataServiceException.class, () -> new ImdbApiCaller(webClient).findAllMovieIdsForTitle("sparta"));
    }

    @Test
    public void fetchMovieById_NullGiven_ReturnNull() {
        ImdbMovieResponse data = defaultImdbApiCaller.fetchMovieById(null);

        Assertions.assertNull(data);
        Mockito.verifyNoInteractions(mockWebClient);
    }

    @Test
    public void fetchMovieById_EmptyStringGiven_ReturnNull() {
        ImdbMovieResponse data = defaultImdbApiCaller.fetchMovieById("");

        Assertions.assertNull(data);
        Mockito.verifyNoInteractions(mockWebClient);
    }

    @Test
    public void fetchMovieById_ValidIdGiven_ReturnData() {
        WebClient webClient = WebClientTestUtil.buildWebClientForResponseFromResourceFile("imdb_api_responses/getMovieByImdbId-response.json");

        ImdbMovieResponse data = new ImdbApiCaller(webClient).fetchMovieById("tt0011");

        Assertions.assertNotNull(data);
        Assertions.assertEquals(
            "ImdbMovieResponse(movieData=ImdbMovieData(imdbId=tt0086250, title=Scarface, year=1983, popularity=422, description=Tony Montana manages to leave Cuba during the Mariel exodus of 1980. He finds himself in a Florida refugee camp but his friend Manny has a way out for them: undertake a contract killing and arrangements will be made to get a green card. He's soon working for drug dealer Frank Lopez and shows his mettle when a deal with Colombian drug dealers goes bad. He also brings a new level of violence to Miami. Tony is protective of his younger sister but his mother knows what he does for a living and disowns him. Tony is impatient and wants it all however, including Frank's empire and his mistress Elvira Hancock. Once at the top however, Tony's outrageous actions make him a target and everything comes crumbling down., rating=8.3, trailer=, imgUrl=https://m.media-amazon.com/images/M/MV5BNjdjNGQ4NDEtNTEwYS00MTgxLTliYzQtYzE2ZDRiZjFhZmNlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_UX182_CR0,0,182,268_AL_.jpg, banner=https://m.media-amazon.com/images/M/MV5BNjdjNGQ4NDEtNTEwYS00MTgxLTliYzQtYzE2ZDRiZjFhZmNlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg))",
            data.toString());
    }

    @Test
    public void fetchMovieById_ValidTitleGiven_ApiResponseIsBadGateway_ThrowException() {
        WebClient webClient = WebClientTestUtil.buildWebClientForBadResponse(HttpStatus.BAD_GATEWAY);

        Assertions.assertThrows(DataServiceException.class, () -> new ImdbApiCaller(webClient).fetchMovieById("tt0099"));
    }

    @Test
    public void searchMoviesByTitle_NullGiven_ReturnEmptyList() {
        List<ImdbMovieResponse> data = defaultImdbApiCaller.searchMoviesByTitle(null);

        Assertions.assertNotNull(data);
        Assertions.assertTrue(data.isEmpty());
        Mockito.verifyNoInteractions(mockWebClient);
    }

    @Test
    public void searchMoviesByTitle_EmptyStringGiven_ReturnEmptyList() {
        List<ImdbMovieResponse> data = defaultImdbApiCaller.searchMoviesByTitle(null);

        Assertions.assertNotNull(data);
        Assertions.assertTrue(data.isEmpty());
        Mockito.verifyNoInteractions(mockWebClient);
    }

    @Test
    public void searchMoviesByTitle_ValidTitleGiven_ApiResponseIsInternalServerError_ThrowException() {
        WebClient webClient = WebClientTestUtil.buildWebClientForBadResponse(HttpStatus.INTERNAL_SERVER_ERROR);

        Assertions.assertThrows(DataServiceException.class, () -> new ImdbApiCaller(webClient).searchMoviesByTitle("sparta"));
    }

    @Test
    public void searchMoviesByTitle_ValidTitleGiven_ReturnData() {
        WebClient webClient = buildWebClientForSearchMoviesFull();

        List<ImdbMovieResponse> data = new ImdbApiCaller(webClient).searchMoviesByTitle("sparta");

        Assertions.assertNotNull(data);
        Assertions.assertFalse(data.isEmpty());
        Assertions.assertEquals(3, data.size());
    }

    private WebClient buildWebClientForSearchMoviesFull() {
        String titlesResponseJson = ResourceUtil.readResourceFile("imdb_api_responses/getMovieIdByTitle-response.json");
        String movieResponseJson = ResourceUtil.readResourceFile("imdb_api_responses/getMovieByImdbId-response.json");

        final ClientResponse titlesClientResponse = ClientResponse.create(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(titlesResponseJson).build();

        final ClientResponse movieClientResponse = ClientResponse.create(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(movieResponseJson).build();

        return WebClientTestUtil.buildWebClientForMultipleResponses(titlesClientResponse, movieClientResponse, movieClientResponse, movieClientResponse);

    }

}
