package site.ancronik.movie.trailer.searcher.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieTrailerSearchRequest {

    private String searchTitle;

    private String locale;
}
