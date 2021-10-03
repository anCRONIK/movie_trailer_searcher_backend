package site.ancronik.movie.trailer.searcher.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.validator.routines.UrlValidator;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import site.ancronik.movie.trailer.searcher.util.JsonResourceObjectMapper;

import javax.cache.Caching;
import java.io.IOException;
import java.util.List;

@EnableCaching
@org.springframework.boot.test.context.TestConfiguration
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
    public UrlValidator urlValidator() {
        return new UrlValidator(new String[] { "http", "https" });
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = new User("user", "", List.of(new SimpleGrantedAuthority("USER")));

        return new InMemoryUserDetailsManager(List.of(
            basicUser
        ));
    }

}
