package cane.brothers.sample.security.autority;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
class JwtConverterConfig {

    private final UserAuthorityService userAuthorityService;

    @Bean
    JwtTokenGrantedAuthoritiesConverter groupsToRolesConverter() {
        var grantedAuthoritiesConverter = new JwtTokenGrantedAuthoritiesConverter(userAuthorityService);
        grantedAuthoritiesConverter.setAuthoritiesClaimName("username");
        return grantedAuthoritiesConverter;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(JwtTokenGrantedAuthoritiesConverter groupsToRolesConverter) {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(groupsToRolesConverter);
        return jwtAuthenticationConverter;
    }
}
