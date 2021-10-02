package site.ancronik.movie.trailer.searcher.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieTrailerSearchResponse {

    @NonNull
    private String title;

    private List<String> trailers = new ArrayList<>();

}
