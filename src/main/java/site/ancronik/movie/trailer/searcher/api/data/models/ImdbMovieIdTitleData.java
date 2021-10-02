package site.ancronik.movie.trailer.searcher.api.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImdbMovieIdTitleData {

    @JsonProperty("imdb_id")
    private String imdbId;

    private String title;

}
