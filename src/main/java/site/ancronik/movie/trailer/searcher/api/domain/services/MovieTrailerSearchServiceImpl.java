package site.ancronik.movie.trailer.searcher.api.domain.services;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchRequest;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;
import site.ancronik.movie.trailer.searcher.api.domain.repositories.MovieTrailerSearchRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class MovieTrailerSearchServiceImpl implements MovieTrailerSearchService {

    private final List<MovieTrailerSearchRepository> searchRepositories;

    private final int repositorySearchTimeoutInSeconds;

    @Autowired
    public MovieTrailerSearchServiceImpl(List<MovieTrailerSearchRepository> searchRepositories, @Value("${app.search-repository-max-duration-in-seconds}") int repositorySearchTimeoutInSeconds) {
        this.searchRepositories = searchRepositories;
        this.repositorySearchTimeoutInSeconds = repositorySearchTimeoutInSeconds;
    }

    //TODO Redis for caching
    @Override
    @Cacheable(value = "movieTrailersSearchServiceCache", key = "#request")
    public List<MovieTrailerSearchResponse> searchMovieTrailersForTitle(@NonNull MovieTrailerSearchRequest request) {
        Set<MovieTrailerSearchResponse> responseSet = new HashSet<>();

        if (request.getLimit() == 0 || request.getLimit() < -1) {
            return new ArrayList<>();
        }

        for (MovieTrailerSearchRepository repository : searchRepositories) {
            try {
                if (responseSet.size() < request.getLimit() || request.getLimit() == -1) {
                    if (request.getLimit() == -1) {
                        responseSet.addAll(callRepository(repository, request));
                    } else {
                        responseSet.addAll(callRepository(repository, request.copyWithLimit(request.getLimit() - responseSet.size())));
                    }
                }
            } catch (Exception e) {
                if (e instanceof TimeoutException || e instanceof InterruptedException) {
                    log.error("Timeout while searching repository {}, it didn't execute in needed time {}", repository.getClass(), repositorySearchTimeoutInSeconds);
                } else {
                    log.error("Exception while searching repository {}", repository.toString(), e);
                }
            }
        }

        return transformSetToList(responseSet, request);
    }

    private List<MovieTrailerSearchResponse> transformSetToList(Set<MovieTrailerSearchResponse> responseSet, MovieTrailerSearchRequest request) {
        List<MovieTrailerSearchResponse> responseList = new ArrayList<>(responseSet);
        if (responseList.size() > request.getLimit() && request.getLimit() > 0) {
            return new ArrayList<>(responseList.subList(0, request.getLimit()));
        }
        return responseList;
    }

    private List<MovieTrailerSearchResponse> callRepository(MovieTrailerSearchRepository repository, MovieTrailerSearchRequest request)
        throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<List<MovieTrailerSearchResponse>> future = CompletableFuture.supplyAsync(() -> repository.findAllMovieTrailersForName(request))
            .exceptionally((e) -> {
                log.error("Exception while searching repository {}", repository.toString(), e);
                return new ArrayList<>();
            });
        return future.get(repositorySearchTimeoutInSeconds, TimeUnit.SECONDS);
    }

}
