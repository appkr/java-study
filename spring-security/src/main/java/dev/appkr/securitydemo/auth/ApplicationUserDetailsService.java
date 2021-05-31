package dev.appkr.securitydemo.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUserDetailsService implements UserDetailsService {

  private final ApplicationUserDao applicationUserDao;

  public ApplicationUserDetailsService(@Qualifier("fake") ApplicationUserDao applicationUserDao) {
    this.applicationUserDao = applicationUserDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return applicationUserDao.findUserByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
  }
}
