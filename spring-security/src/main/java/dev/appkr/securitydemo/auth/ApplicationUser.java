package dev.appkr.securitydemo.auth;

import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationUser implements UserDetails {

  private final String username;
  private final String password;
  private final Set<? extends GrantedAuthority> grantedAuthorities;
  private final boolean enabled;

  public ApplicationUser(String username, String password,
      Set<? extends GrantedAuthority> grantedAuthorities, boolean enabled) {
    this.username = username;
    this.password = password;
    this.grantedAuthorities = grantedAuthorities;
    this.enabled = enabled;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return enabled;
  }

  @Override
  public boolean isAccountNonLocked() {
    return enabled;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return enabled;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
