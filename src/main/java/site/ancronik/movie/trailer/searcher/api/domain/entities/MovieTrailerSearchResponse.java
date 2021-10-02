package site.ancronik.movie.trailer.searcher.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieTrailerSearchResponse implements Serializable, Comparable<MovieTrailerSearchResponse> {

    @NonNull
    private String title;

    private List<String> trailers = new ArrayList<>();

    @Override
    public int compareTo(MovieTrailerSearchResponse other) {
        if (null == other) {
            return -1;
        }
        if (title.equalsIgnoreCase(other.getTitle())) {
            return 0;
        }
        return other.getTrailers().containsAll(trailers) ? 0 : title.compareTo(other.getTitle());
    }

}
