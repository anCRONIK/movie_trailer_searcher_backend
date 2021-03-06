package site.ancronik.movie.trailer.searcher.api.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ImdbMovieTitleSearchResponse {

    @JsonProperty("Data")
    List<ImdbMovieIdTitleData> data;

}
