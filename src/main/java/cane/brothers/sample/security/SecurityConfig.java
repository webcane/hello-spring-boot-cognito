package cane.brothers.sample.security;

import static org.springdoc.core.utils.Constants.SWAGGER_UI_PATH;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
class SecurityConfig {

    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Value(SWAGGER_UI_PATH)
    private String swaggerUiPath;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // disable default behaviour
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.cors(withDefaults());

        // We don't need CSRF for JWT-based authentication
        http.csrf(AbstractHttpConfigurer::disable);

        // restrict access to endpoints
        http.authorizeHttpRequests(req -> req
                // permits
                .requestMatchers(new OrRequestMatcher(antMatcher("/"),
                        antMatcher("/login"),
                        antMatcher("/v3/api-docs/**"),
                        antMatcher("/swagger-ui*/**")
                ))
                .permitAll()
                .requestMatchers(antMatcher("/favicon.ico"))
                .permitAll()
                .anyRequest().authenticated());

        // throw Access Denied exception only once
        http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN)));

        // disables session creation on Spring Security
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        // oauth2 cognito
        http.oauth2Login(c -> c.loginPage(swaggerUiPath)
                .defaultSuccessUrl(swaggerUiPath));
        http.oauth2ResourceServer(c -> c.jwt(
                j -> j.jwtAuthenticationConverter(jwtAuthenticationConverter)));

        return http.build();
    }

    /**
     * The purpose of this method is to exclude from the Spring security chain the URL's specific to Swagger UI and Actuator.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/webjars/**", "/favicon.ico");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(CorsEndpointProperties corsProperties) {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsProperties.toCorsConfiguration());
        return source;
    }
}
