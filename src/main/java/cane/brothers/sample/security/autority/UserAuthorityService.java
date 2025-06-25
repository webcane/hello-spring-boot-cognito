package cane.brothers.sample.security.autority;

import java.util.List;

interface UserAuthorityService {

    List<String> getUserAuthorities(String username);
}
