package site.ancronik.movie.trailer.searcher.api.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImdbMovieData {

    @JsonProperty("imdb_id")
    private String imdbId;

    private String title;

    private String year;

    private String popularity;

    private String description;

    private Double rating;

    private String trailer;

    @JsonProperty("image_url")
    private String imgUrl;

    private String banner;

}
