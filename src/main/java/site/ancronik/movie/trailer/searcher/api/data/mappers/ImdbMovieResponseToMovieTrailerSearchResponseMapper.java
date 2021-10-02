package site.ancronik.movie.trailer.searcher.api.data.mappers;

import org.springframework.stereotype.Component;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieResponse;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;
import site.ancronik.movie.trailer.searcher.core.Mapper;

import java.util.Collections;

@Component
public class ImdbMovieResponseToMovieTrailerSearchResponseMapper implements Mapper<ImdbMovieResponse, MovieTrailerSearchResponse> {

    @Override
    public MovieTrailerSearchResponse map(ImdbMovieResponse from) {
        MovieTrailerSearchResponse response = new MovieTrailerSearchResponse();

        response.setTitle(from.getTitle());
        response.setTrailerUrls(Collections.singletonList(from.getTrailer()));

        return response;
    }

}
