package site.ancronik.movie.trailer.searcher.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.ancronik.movie.trailer.searcher.util.JsonResourceObjectMapper;

@Configuration
public class TestConfiguration {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    JsonResourceObjectMapper jsonResourceObjectMapper(ObjectMapper objectMapper) {
        return new JsonResourceObjectMapper(objectMapper);
    }

}
