package site.ancronik.movie.trailer.searcher.core.util;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import site.ancronik.movie.trailer.searcher.core.exception.DataServiceException;

import java.util.function.Function;

public final class WebClientUtil {

    private WebClientUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T> Function<ClientResponse, Mono<? extends Throwable>> throwDataServiceException(HttpMethod method, String uri, T params) {
        return response -> Mono.error(new DataServiceException(method.name(), String.format("[%s] [%s] with params [%s]", response.statusCode(), uri, params)));
    }

    public static Function<ClientResponse, Mono<? extends Throwable>> throwDataServiceException(HttpMethod method, String message) {
        return response -> Mono.error(new DataServiceException(method.name(), String.format("[%s] %s", response.statusCode(), message)));
    }

}
