package site.ancronik.movie.trailer.searcher.config;

import org.ehcache.jsr107.EhcacheCachingProvider;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import site.ancronik.movie.trailer.searcher.api.domain.repositories.MovieTrailerSearchRepository;
import site.ancronik.movie.trailer.searcher.api.domain.services.MovieTrailerSearchService;

import javax.cache.Caching;
import java.io.IOException;

@EnableCaching
@Configuration
@ComponentScan(basePackageClasses = MovieTrailerSearchService.class)
public class ServiceDefaultConfiguration {

    @MockBean(name = "youtubeRepositoryMock")
    MovieTrailerSearchRepository youtubeRepositoryMock;

    @MockBean(name = "netflixRepositoryMock")
    MovieTrailerSearchRepository netflixRepositoryMock;

    @Bean(destroyMethod = "close")
    public javax.cache.CacheManager cacheManager() throws IOException {
        XmlConfiguration xmlConfig = new XmlConfiguration(new ClassPathResource("ehcache.xml").getURL());
        EhcacheCachingProvider provider = (EhcacheCachingProvider) Caching.getCachingProvider();
        return provider.getCacheManager(provider.getDefaultURI(), xmlConfig);

    }

    @Bean
    public JCacheCacheManager jCacheCacheManager(javax.cache.CacheManager cacheManager) {
        return new JCacheCacheManager(cacheManager);
    }

}
