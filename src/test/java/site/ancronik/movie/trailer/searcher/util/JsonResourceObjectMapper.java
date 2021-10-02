package site.ancronik.movie.trailer.searcher.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public final class JsonResourceObjectMapper {

    private final ObjectMapper objectMapper;

    public JsonResourceObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T loadTestJson(String fileName, Class<T> clazz) throws IOException {
        return loadJson(new ClassPathResource(fileName).getFile(), clazz);
    }

    private <T> T loadJson(File file, Class<T> clazz) throws IOException {
        return objectMapper.readValue(file, clazz);
    }

}
