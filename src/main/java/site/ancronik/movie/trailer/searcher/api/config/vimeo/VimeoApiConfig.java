package site.ancronik.movie.trailer.searcher.api.config.vimeo;

import com.vimeo.networking2.Authenticator;
import com.vimeo.networking2.ScopeType;
import com.vimeo.networking2.VimeoApiClient;
import com.vimeo.networking2.config.VimeoApiConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class VimeoApiConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.vimeo")
    public VimeoApiConfigProperties vimeoApiConfigProperties() {
        return new VimeoApiConfigProperties();
    }

/*    public VimeoApiClient vimeoApiClient(VimeoApiConfigProperties configProperties) {
        VimeoApiConfiguration vimeoApiConfiguration = new VimeoApiConfiguration.Builder(configProperties.getClientId(),
            configProperties.getClientSecret(), List.of(ScopeType.PUBLIC)).build();

        VimeoApiClient.initialize(vimeoApiConfiguration, Authenticator.instance());

    }*/

}
