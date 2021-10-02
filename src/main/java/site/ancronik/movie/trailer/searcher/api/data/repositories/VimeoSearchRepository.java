package site.ancronik.movie.trailer.searcher.api.data.repositories;

import lombok.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchRequest;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;
import site.ancronik.movie.trailer.searcher.api.domain.repositories.MovieTrailerSearchRepository;

import java.util.ArrayList;
import java.util.List;

@Component("vimeoSearchRepository")
public class VimeoSearchRepository implements MovieTrailerSearchRepository {

    @Override
    @Cacheable(value="movieTrailersSearchCache", key = "#request.searchTitle")
    public List<MovieTrailerSearchResponse> findAllMovieTrailersForName(@NonNull MovieTrailerSearchRequest request) {
        return new ArrayList<>();
    }

}
