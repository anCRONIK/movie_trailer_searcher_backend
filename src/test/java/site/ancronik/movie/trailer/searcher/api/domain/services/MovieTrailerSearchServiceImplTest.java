package site.ancronik.movie.trailer.searcher.api.domain.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchRequest;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;
import site.ancronik.movie.trailer.searcher.api.domain.repositories.MovieTrailerSearchRepository;
import site.ancronik.movie.trailer.searcher.config.ServiceDefaultConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ContextConfiguration(classes = { ServiceDefaultConfiguration.class })
@SpringBootTest
public class MovieTrailerSearchServiceImplTest {

    @Autowired
    @Qualifier("youtubeRepositoryMock")
    MovieTrailerSearchRepository youtubeRepositoryMock;

    @Autowired
    @Qualifier("netflixRepositoryMock")
    MovieTrailerSearchRepository netflixRepositoryMock;

    @Autowired
    MovieTrailerSearchService autowiredService;

    MovieTrailerSearchServiceImpl movieTrailerSearchService;


    @BeforeEach
    void initBeforeEachTest() {
        movieTrailerSearchService = new MovieTrailerSearchServiceImpl(Arrays.asList(youtubeRepositoryMock, netflixRepositoryMock));
    }

    MovieTrailerSearchRequest defaultRequest = new MovieTrailerSearchRequest("sparta", 10);

    @Test
    public void checkInit() {
        Assertions.assertNotNull(movieTrailerSearchService);
    }

    @Test
    public void searchMovieTrailersForTitle_NullGiven_ThrowNUP() {
        Assertions.assertThrows(NullPointerException.class, () -> movieTrailerSearchService.searchMovieTrailersForTitle(null));

        Mockito.verifyNoInteractions(netflixRepositoryMock);
        Mockito.verifyNoInteractions(youtubeRepositoryMock);
    }

    @Test
    public void searchMovieTrailersForTitle_ValidRequestGiven_OneOfTheRepositoriesThrowsException_ReturnValuesOnlyFromAnother() {
        List<MovieTrailerSearchResponse> netflixResponses = generateSearchResponses(3);

        Mockito.when(youtubeRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenThrow(RuntimeException.class);
        Mockito.when(netflixRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenReturn(netflixResponses);

        List<MovieTrailerSearchResponse> response = movieTrailerSearchService.searchMovieTrailersForTitle(defaultRequest);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertTrue(netflixResponses.containsAll(response));

        Mockito.verify(youtubeRepositoryMock).findAllMovieTrailersForName(Mockito.any());
        Mockito.verify(netflixRepositoryMock).findAllMovieTrailersForName(Mockito.any());

        Mockito.verifyNoMoreInteractions(netflixRepositoryMock);
        Mockito.verifyNoMoreInteractions(youtubeRepositoryMock);
    }

    @Test
    public void searchMovieTrailersForTitle_ValidRequestGiven_ReturnValuesFromAllRepositoriesBecauseTheyAreNoMoreThanLimit() {
        List<MovieTrailerSearchResponse> netflixResponses = generateSearchResponses(3);
        List<MovieTrailerSearchResponse> youtubeResponses = generateSearchResponses(2);

        List<MovieTrailerSearchResponse> testResponses = new ArrayList<>(netflixResponses);
        testResponses.addAll(youtubeResponses);

        Mockito.when(youtubeRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenReturn(youtubeResponses);
        Mockito.when(netflixRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenReturn(netflixResponses);

        List<MovieTrailerSearchResponse> response = movieTrailerSearchService.searchMovieTrailersForTitle(defaultRequest);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertTrue(response.containsAll(testResponses));

        Mockito.verify(youtubeRepositoryMock).findAllMovieTrailersForName(Mockito.any());
        Mockito.verify(netflixRepositoryMock).findAllMovieTrailersForName(Mockito.any());

        Mockito.verifyNoMoreInteractions(netflixRepositoryMock);
        Mockito.verifyNoMoreInteractions(youtubeRepositoryMock);
    }

    @Test
    public void searchMovieTrailersForTitle_ValidRequestGiven_ReturnEmptyListBecauseLimitIs0() {
        List<MovieTrailerSearchResponse> netflixResponses = generateSearchResponses(3);
        List<MovieTrailerSearchResponse> youtubeResponses = generateSearchResponses(2);

        Mockito.when(youtubeRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenReturn(youtubeResponses);
        Mockito.when(netflixRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenReturn(netflixResponses);

        List<MovieTrailerSearchResponse> response = movieTrailerSearchService.searchMovieTrailersForTitle(new MovieTrailerSearchRequest("sparta", 0));

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());

        Mockito.verifyNoInteractions(netflixRepositoryMock);
        Mockito.verifyNoInteractions(youtubeRepositoryMock);
    }

    @Test
    public void searchMovieTrailersForTitle_ValidRequestGiven_ReturnEmptyListBecauseLimitIsLowerThanMinus1() {
        List<MovieTrailerSearchResponse> netflixResponses = generateSearchResponses(3);
        List<MovieTrailerSearchResponse> youtubeResponses = generateSearchResponses(2);

        Mockito.when(youtubeRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenReturn(youtubeResponses);
        Mockito.when(netflixRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenReturn(netflixResponses);

        List<MovieTrailerSearchResponse> response = movieTrailerSearchService.searchMovieTrailersForTitle(new MovieTrailerSearchRequest("sparta", -2));

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());

        Mockito.verifyNoInteractions(netflixRepositoryMock);
        Mockito.verifyNoInteractions(youtubeRepositoryMock);
    }

    @Test
    public void searchMovieTrailersForTitle_ValidRequestGiven_FirstRepositoryReturnsMoreValuesThanLimit_DoNotCallOtherRepositoriesAndReturnLimitedValues() {
        List<MovieTrailerSearchResponse> netflixResponses = generateSearchResponses(2);
        List<MovieTrailerSearchResponse> youtubeResponses = generateSearchResponses(5);

        Mockito.when(youtubeRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenReturn(youtubeResponses);
        Mockito.when(netflixRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenReturn(netflixResponses);

        List<MovieTrailerSearchResponse> response = movieTrailerSearchService.searchMovieTrailersForTitle(new MovieTrailerSearchRequest("sparta", 3));

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(3, response.size());
        Assertions.assertTrue(youtubeResponses.containsAll(response));
        for (MovieTrailerSearchResponse r : netflixResponses) {
            Assertions.assertFalse(youtubeResponses.contains(r));
        }

        Mockito.verify(youtubeRepositoryMock).findAllMovieTrailersForName(Mockito.any());
        Mockito.verifyNoInteractions(netflixRepositoryMock);
        Mockito.verifyNoMoreInteractions(youtubeRepositoryMock);
    }

    @Test
    public void searchMovieTrailersForTitle_ValidRequestGiven_TestCacheable_OnSecondCallReturnValuesFromCache() {
        List<MovieTrailerSearchResponse> netflixResponses = generateSearchResponses(2);
        List<MovieTrailerSearchResponse> youtubeResponses = generateSearchResponses(5);

        Mockito.when(youtubeRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenReturn(youtubeResponses);
        Mockito.when(netflixRepositoryMock.findAllMovieTrailersForName(Mockito.any())).thenReturn(netflixResponses);

        List<MovieTrailerSearchResponse> response = autowiredService.searchMovieTrailersForTitle(new MovieTrailerSearchRequest("sparta", 3));

        List<MovieTrailerSearchResponse> response2 = autowiredService.searchMovieTrailersForTitle(new MovieTrailerSearchRequest("sparta", 3));

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(3, response.size());
        Assertions.assertTrue(youtubeResponses.containsAll(response));
        for (MovieTrailerSearchResponse r : netflixResponses) {
            Assertions.assertFalse(youtubeResponses.contains(r));
        }
        Assertions.assertEquals(response, response2);

        Mockito.verify(youtubeRepositoryMock).findAllMovieTrailersForName(Mockito.any());
        Mockito.verifyNoInteractions(netflixRepositoryMock);
        Mockito.verifyNoMoreInteractions(youtubeRepositoryMock);
    }

    private List<MovieTrailerSearchResponse> generateSearchResponses(int n) {
        List<MovieTrailerSearchResponse> list = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < n; ++i) {
            list.add(new MovieTrailerSearchResponse("Roki" + random.nextInt() + "-" + random.nextDouble(), List.of("http://roki.com/" + random.nextInt() + "-" + random.nextDouble())));
        }

        return list;
    }

}
