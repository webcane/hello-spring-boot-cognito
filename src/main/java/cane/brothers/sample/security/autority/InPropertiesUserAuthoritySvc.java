package cane.brothers.sample.security.autority;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cane.brothers.sample.AppProperties;
import lombok.RequiredArgsConstructor;

/**
 * Class responsible for preloading known system users and their granted groups.
 * Example:
 * <pre>
 * {@code
 * app:
 *   users:
 *     user1:
 *       groups:
 *         - GROUP1
 *     user1:
 *       groups:
 *         - GROUP1
 *         - GROUP2
 * }
 * </pre>
 */
@Service
@RequiredArgsConstructor
class InPropertiesUserAuthoritySvc implements UserAuthorityService {

    private final AppProperties appProperties;

    @Override
    public List<String> getUserAuthorities(final String username) {
        Assert.hasText(username, "username cannot be empty");
        // If username contains an underscore symbol, it might cause issues with Spring Boot properties
        var user = username.replace("_", "");
        return appProperties.users().get(user).groups();
    }
}
