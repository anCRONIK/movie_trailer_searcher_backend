package site.ancronik.movie.trailer.searcher.api.domain.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchRequest;
import site.ancronik.movie.trailer.searcher.api.domain.repositories.MovieTrailerSearchRepository;

@ContextConfiguration(classes = { MovieTrailerSearchServiceImpl.class })
@SpringBootTest
public class MovieTrailerSearchServiceImplTest {

    @MockBean(name = "youtubeRepositoryMock")
    MovieTrailerSearchRepository youtubeRepositoryMock;

    @MockBean(name = "netflixRepositoryMock")
    MovieTrailerSearchRepository netflixRepositoryMock;

    @Autowired
    MovieTrailerSearchServiceImpl movieTrailerSearchService;

    MovieTrailerSearchRequest defaultRequest = new MovieTrailerSearchRequest("sparta", 10);

    @Test
    public void checkInit() {
        Assertions.assertNotNull(movieTrailerSearchService);
    }

}
