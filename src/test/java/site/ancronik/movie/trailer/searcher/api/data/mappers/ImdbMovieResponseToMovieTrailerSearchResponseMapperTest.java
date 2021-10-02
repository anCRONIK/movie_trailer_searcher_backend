package site.ancronik.movie.trailer.searcher.api.data.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import site.ancronik.movie.trailer.searcher.api.data.mappers.ImdbMovieResponseToMovieTrailerSearchResponseMapper;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieData;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieResponse;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;

public class ImdbMovieResponseToMovieTrailerSearchResponseMapperTest {

    private ImdbMovieResponseToMovieTrailerSearchResponseMapper mapper = new ImdbMovieResponseToMovieTrailerSearchResponseMapper();

    @Test
    public void map_NullGiven_ThrowNUP() {
        Assertions.assertThrows(NullPointerException.class, () -> mapper.map(null));
    }

    @Test
    public void map_ObjectWithNullDataGiven_ThrowNUP() {
        ImdbMovieResponse imdbMovieResponse = new ImdbMovieResponse();

        Assertions.assertThrows(NullPointerException.class, () -> mapper.map(imdbMovieResponse));
    }

    @Test
    public void map_ObjectWithNullValuesGiven_ReturnObjectWihEmptyValues() {
        ImdbMovieResponse imdbMovieResponse = new ImdbMovieResponse();
        imdbMovieResponse.setMovieData(new ImdbMovieData());

        MovieTrailerSearchResponse response = mapper.map(imdbMovieResponse);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getTitle().isEmpty());
        Assertions.assertNotNull(response.getTrailers());
        Assertions.assertTrue(response.getTrailers().isEmpty());
    }

    @Test
    public void map_ValidObjectGiven_ReturnValidMappedObject() {
        ImdbMovieResponse imdbMovieResponse = new ImdbMovieResponse();
        ImdbMovieData movie = new ImdbMovieData();
        movie.setImdbId("2313");
        movie.setTitle("title");
        movie.setTrailer("https://trailer.com");
        imdbMovieResponse.setMovieData(movie);

        MovieTrailerSearchResponse response = mapper.map(imdbMovieResponse);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getTitle().isEmpty());
        Assertions.assertEquals("title", response.getTitle());
        Assertions.assertNotNull(response.getTrailers());
        Assertions.assertFalse(response.getTrailers().isEmpty());
        Assertions.assertEquals(1, response.getTrailers().size());
        Assertions.assertEquals("https://trailer.com", response.getTrailers().get(0));
    }

}
