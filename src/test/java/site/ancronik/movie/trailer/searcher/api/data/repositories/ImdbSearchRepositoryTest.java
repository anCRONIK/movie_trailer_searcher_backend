package site.ancronik.movie.trailer.searcher.api.data.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import site.ancronik.movie.trailer.searcher.api.config.ApiRepositoryDefaultConfiguration;
import site.ancronik.movie.trailer.searcher.api.data.drivers.ImdbApiCaller;
import site.ancronik.movie.trailer.searcher.api.data.mappers.ImdbMovieResponseToMovieTrailerSearchResponseMapper;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieData;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieResponse;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchRequest;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;
import site.ancronik.movie.trailer.searcher.api.domain.repositories.MovieTrailerSearchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ContextConfiguration(classes = { ApiRepositoryDefaultConfiguration.class })
@SpringBootTest
public class ImdbSearchRepositoryTest {

    @Autowired
    ImdbApiCaller imdbApiCaller;

    @Autowired
    ImdbMovieResponseToMovieTrailerSearchResponseMapper imdbMovieResponseToMovieTrailerSearchResponseMapper;

    @Autowired
    @Qualifier("imdbSearchRepository")
    MovieTrailerSearchRepository autowiredRepository;

    ImdbSearchRepository imdbSearchRepository;

    MovieTrailerSearchRequest defaultRequest = new MovieTrailerSearchRequest("sparta", 4);

    @BeforeEach
    void initBeforeEachTest() {
        imdbSearchRepository = new ImdbSearchRepository(imdbApiCaller, imdbMovieResponseToMovieTrailerSearchResponseMapper);
    }

    @Test
    public void checkInit() {
        Assertions.assertNotNull(autowiredRepository);
        Assertions.assertNotNull(imdbSearchRepository);
    }

    @Test
    public void searchMovieTrailersForTitle_NullGiven_ThrowNUP() {
        Assertions.assertThrows(NullPointerException.class, () -> imdbSearchRepository.findAllMovieTrailersForName(null));

        Mockito.verifyNoInteractions(imdbApiCaller);
    }

    @Test
    public void searchMovieTrailersForTitle_ValidRequestGiven_ApiCallerThrowsException_ThrowException() {
        Mockito.when(imdbApiCaller.searchMoviesByTitle(Mockito.anyString())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> imdbSearchRepository.findAllMovieTrailersForName(defaultRequest));

        Mockito.verify(imdbApiCaller).searchMoviesByTitle(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(imdbApiCaller);
    }

    @Test
    public void searchMovieTrailersForTitle_ValidRequestGiven_ReturnDataFromApiCaller() {
        Mockito.when(imdbApiCaller.searchMoviesByTitle(Mockito.anyString())).thenReturn(generateSearchResponses(3));

        List<MovieTrailerSearchResponse> response = imdbSearchRepository.findAllMovieTrailersForName(defaultRequest);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(3, response.size());

        Mockito.verify(imdbApiCaller).searchMoviesByTitle(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(imdbApiCaller);
    }

    private List<ImdbMovieResponse> generateSearchResponses(int n) {
        List<ImdbMovieResponse> list = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < n; ++i) {
            ImdbMovieResponse response = new ImdbMovieResponse();
            ImdbMovieData movieData = new ImdbMovieData();
            movieData.setTrailer("https://roki.com/" + random.nextInt() + "-" + random.nextDouble());
            movieData.setTitle("Roki" + random.nextInt() + "-" + random.nextDouble());
            response.setMovieData(movieData);
            list.add(response);
        }

        return list;
    }

}
