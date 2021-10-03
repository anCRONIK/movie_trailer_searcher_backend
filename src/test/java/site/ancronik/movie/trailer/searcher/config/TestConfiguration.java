package site.ancronik.movie.trailer.searcher.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.validator.routines.UrlValidator;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import site.ancronik.movie.trailer.searcher.util.JsonResourceObjectMapper;

import javax.cache.Caching;
import java.io.IOException;

@EnableCaching
@Configuration
public class TestConfiguration {

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


    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    JsonResourceObjectMapper jsonResourceObjectMapper(ObjectMapper objectMapper) {
        return new JsonResourceObjectMapper(objectMapper);
    }

    @Bean
    public UrlValidator urlValidator(){
        return new UrlValidator(new String[] { "http", "https" });
    }
}
