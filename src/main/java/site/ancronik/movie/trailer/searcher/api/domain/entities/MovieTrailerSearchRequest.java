package site.ancronik.movie.trailer.searcher.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieTrailerSearchRequest implements Serializable {

    private String searchTitle;

    private int limit;

    public MovieTrailerSearchRequest copyWithLimit(int newLimit) {
        return new MovieTrailerSearchRequest(this.searchTitle, newLimit);
    }

}
