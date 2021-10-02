package site.ancronik.movie.trailer.searcher.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientTestUtil {

    public static WebClient buildWebClientForResponseFromResourceFile(String fileName) {
        String responseJson = ResourceUtil.readResourceFile(fileName);

        final ClientResponse clientResponse = ClientResponse.create(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(responseJson).build();

        return buildWebClientForResponse(clientResponse);
    }

    public static WebClient buildWebClientForBadResponse(HttpStatus badResponse) {
        final ClientResponse clientResponse = ClientResponse.create(badResponse).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

        return buildWebClientForResponse(clientResponse);
    }

    public static WebClient buildWebClientForResponse(ClientResponse clientResponse) {
        ExchangeFunction responseExchangeFunction = request -> Mono.just(clientResponse);

        return WebClient.builder().exchangeFunction(responseExchangeFunction).build();
    }

    public static WebClient buildWebClientForMultipleResponses(ClientResponse... clientResponses) {
        ExchangeFunction responseExchangeFunction = new MultipleSequentialResponsesExchangeFunction(clientResponses);

        return WebClient.builder().exchangeFunction(responseExchangeFunction).build();
    }

}
