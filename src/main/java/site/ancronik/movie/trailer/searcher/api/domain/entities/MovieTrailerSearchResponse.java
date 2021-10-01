package site.ancronik.movie.trailer.searcher.api.domain.entities;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class MovieTrailerSearchResponse {

    @NonNull
    private final String name;

    private final List<String> trailerUrls;

}
