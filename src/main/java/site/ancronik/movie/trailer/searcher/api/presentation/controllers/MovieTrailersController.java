package site.ancronik.movie.trailer.searcher.api.presentation.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchRequest;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;
import site.ancronik.movie.trailer.searcher.api.domain.services.MovieTrailerSearchService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/movie-trailers")
@Slf4j
public class MovieTrailersController {

    private final MovieTrailerSearchService movieTrailersSearchService;

    @Autowired
    public MovieTrailersController(MovieTrailerSearchService movieTrailersSearchService) {
        this.movieTrailersSearchService = movieTrailersSearchService;
    }

    @Async
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<List<MovieTrailerSearchResponse>>> searchMovieTrailersByTitle(@RequestParam("title") String title,
        @RequestParam(name = "limit", defaultValue = "10") int limit) {
        log.info("New search for movie trailer '{}' with limit {}", title, limit);

        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(movieTrailersSearchService.searchMovieTrailersForTitle(new MovieTrailerSearchRequest(title, limit))))
            .exceptionally(e -> ResponseEntity.badRequest().build());
    }

}
