package site.ancronik.movie.trailer.searcher.api.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import site.ancronik.movie.trailer.searcher.api.domain.repositories.MovieTrailerSearchRepository;
import site.ancronik.movie.trailer.searcher.api.domain.services.MovieTrailerSearchService;
import site.ancronik.movie.trailer.searcher.config.TestConfiguration;

@Configuration
@ComponentScan(basePackageClasses = MovieTrailerSearchService.class)
@Import({ TestConfiguration.class })
public class ApiServiceDefaultConfiguration {

    @MockBean(name = "youtubeRepositoryMock")
    MovieTrailerSearchRepository youtubeRepositoryMock;

    @MockBean(name = "netflixRepositoryMock")
    MovieTrailerSearchRepository netflixRepositoryMock;

}
