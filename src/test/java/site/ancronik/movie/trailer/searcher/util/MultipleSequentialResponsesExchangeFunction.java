package site.ancronik.movie.trailer.searcher.util;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class MultipleSequentialResponsesExchangeFunction implements ExchangeFunction {

    private int requestCounter = 0;

    private final ClientResponse[] responses;

    @Override
    public Mono<ClientResponse> exchange(ClientRequest request) {
        return Mono.just(responses[requestCounter++]);
    }

}