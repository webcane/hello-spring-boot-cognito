package cane.brothers.sample.security.autority;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

/**
 * There is a problem with OAuth2 authorization in Spring Security. To authorize a user, knowledge of their authorities is required.
 * User groups belong to the id_token (see "cognito:groups" claim), but the user uses only the access_token.
 * This class helps to identify a user by the principal claim and load their authorities.
 */
@RequiredArgsConstructor
class JwtTokenGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String DEFAULT_AUTHORITY_PREFIX = "ROLE_"; // SCOPE_
    private final UserAuthorityService userAuthorityService;

    private String principalClaimName = JwtClaimNames.SUB;
    private String authorityPrefix = DEFAULT_AUTHORITY_PREFIX;

    @Override
    public Collection<GrantedAuthority> convert(final Jwt source) {
        // Use the appropriate claim that uniquely identifies the user
        String claim = source.getClaimAsString(principalClaimName);
        var authorities = userAuthorityService.getUserAuthorities(claim);

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(this.authorityPrefix + authority));
        }

        return grantedAuthorities;
    }

    public void setAuthorityPrefix(String authorityPrefix) {
        Assert.notNull(authorityPrefix, "authorityPrefix cannot be null");
        this.authorityPrefix = authorityPrefix;
    }

    public void setAuthoritiesClaimName(String authoritiesClaimName) {
        Assert.notNull(authoritiesClaimName, "authoritiesClaimName cannot be null");
        this.principalClaimName = authoritiesClaimName;
    }
}
