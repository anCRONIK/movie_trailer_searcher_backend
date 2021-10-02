package site.ancronik.movie.trailer.searcher.api.domain.services;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchRequest;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;
import site.ancronik.movie.trailer.searcher.api.domain.repositories.MovieTrailerSearchRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class MovieTrailerSearchServiceImpl implements MovieTrailerSearchService {

    private final List<MovieTrailerSearchRepository> searchRepositories;

    @Autowired
    public MovieTrailerSearchServiceImpl(List<MovieTrailerSearchRepository> searchRepositories) {
        this.searchRepositories = searchRepositories;
    }

    //TODO Redis for caching
    @Override
    public List<MovieTrailerSearchResponse> searchMovieTrailersForTitle(@NonNull MovieTrailerSearchRequest request) {
        Set<MovieTrailerSearchResponse> responseSet = new HashSet<>();

        for (MovieTrailerSearchRepository repository : searchRepositories) {
            try {
                if (responseSet.size() < request.getLimit() && request.getLimit() >= 0) {
                    responseSet.addAll(repository.findAllMovieTrailersForName(request));
                } else if (request.getLimit() == -1) {
                    responseSet.addAll(repository.findAllMovieTrailersForName(request));
                }
            } catch (Exception e) {
                log.error("Exception while searching repository {}", repository.getClass(), e);
            }
        }

        return new ArrayList<>(responseSet);
    }

}
