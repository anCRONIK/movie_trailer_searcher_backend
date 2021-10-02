package site.ancronik.movie.trailer.searcher.api.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbMovieResponse {

    @JsonProperty("Data")
    private ImdbMovieData movieData;

}
