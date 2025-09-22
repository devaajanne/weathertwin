package app.weathertwin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This configuration class holds the application's web security config.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CorsConfig corsConfig;

    public WebSecurityConfig(CorsConfig corsConfig) {
        this.corsConfig = corsConfig;
    }

    /**
     * This method configures and returns the application's security filter chain.
     * @param httpSecurity the HttpSecurity instance to configure.
     * @return the configured SecurityFilterChain instance.
     * @throws Exception if an error occurs during the configuration of the security filters.
     */
    @Bean
    public SecurityFilterChain configureSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfig.getCorsConfigurationSource()))
                .csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }
}
