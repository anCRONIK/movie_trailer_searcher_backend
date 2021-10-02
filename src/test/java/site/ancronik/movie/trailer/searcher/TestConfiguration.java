package site.ancronik.movie.trailer.searcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import site.ancronik.movie.trailer.searcher.util.JsonResourceObjectMapper;

@Configuration
public class TestConfiguration {

    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    JsonResourceObjectMapper jsonResourceObjectMapper(ObjectMapper objectMapper){
        return new JsonResourceObjectMapper(objectMapper);
    }
}
