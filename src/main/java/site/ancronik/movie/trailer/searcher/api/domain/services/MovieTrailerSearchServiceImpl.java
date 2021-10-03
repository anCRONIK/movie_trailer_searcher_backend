package site.ancronik.movie.trailer.searcher.api.domain.services;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "movieTrailersSearchServiceCache", key = "#request")
    public List<MovieTrailerSearchResponse> searchMovieTrailersForTitle(@NonNull MovieTrailerSearchRequest request) {
        Set<MovieTrailerSearchResponse> responseSet = new HashSet<>();

        if (request.getLimit() == 0 || request.getLimit() < -1) {
            return new ArrayList<>();
        }

        //TODO we need to make calls async so if one repository is bad we still get data and it does not impact performance
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

        List<MovieTrailerSearchResponse> responseList = new ArrayList<>(responseSet);
        if (responseList.size() > request.getLimit() && request.getLimit() > 0) {
            return new ArrayList<>(responseList.subList(0, request.getLimit()));
        }
        return responseList;
    }

}
