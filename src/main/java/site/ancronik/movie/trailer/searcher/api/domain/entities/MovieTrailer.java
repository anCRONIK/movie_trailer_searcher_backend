package site.ancronik.movie.trailer.searcher.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.net.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieTrailer {

    @NonNull
    private String title;

    @NonNull
    private URL url;

}
