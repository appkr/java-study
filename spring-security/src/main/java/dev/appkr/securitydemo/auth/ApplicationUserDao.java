package dev.appkr.securitydemo.auth;

import java.util.Optional;

public interface ApplicationUserDao {

  Optional<ApplicationUser> findUserByUsername(String username);

}
