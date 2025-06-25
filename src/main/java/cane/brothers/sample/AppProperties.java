package cane.brothers.sample;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(List<UserRole> roles, Map<String, UserAccount> users) {

    public record UserRole(String whitelist) {
    }

    public record UserAccount(List<String> groups) {
    }

}
