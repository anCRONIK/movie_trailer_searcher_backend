package site.ancronik.movie.trailer.searcher.util;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;

public class ResourceUtil {

    @SneakyThrows
    public static String readResourceFile(String fileName) {
        return new String(Files.readAllBytes(new ClassPathResource(fileName).getFile().toPath()));
    }

}
