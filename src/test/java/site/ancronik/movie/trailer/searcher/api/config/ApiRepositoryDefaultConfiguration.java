package site.ancronik.movie.trailer.searcher.api.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import site.ancronik.movie.trailer.searcher.api.data.drivers.ImdbApiCaller;
import site.ancronik.movie.trailer.searcher.api.data.mappers.ImdbMovieResponseToMovieTrailerSearchResponseMapper;
import site.ancronik.movie.trailer.searcher.api.data.repositories.ImdbSearchRepository;
import site.ancronik.movie.trailer.searcher.config.TestConfiguration;

@Configuration
@ComponentScan(basePackageClasses = { ImdbSearchRepository.class, ImdbMovieResponseToMovieTrailerSearchResponseMapper.class })
@Import({ TestConfiguration.class })
public class ApiRepositoryDefaultConfiguration {

    @MockBean
    ImdbApiCaller imdbApiCaller;

}
