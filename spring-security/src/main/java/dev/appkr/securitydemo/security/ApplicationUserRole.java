package dev.appkr.securitydemo.security;

import static dev.appkr.securitydemo.security.ApplicationUserPermission.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
  STUDENT(Collections.emptySet()),
  ADMIN(new HashSet<>() {{
    add(COURSE_READ);
    add(COURSE_WRITE);
    add(STUDENT_READ);
    add(STUDENT_WRITE);
  }}),
  ADMINTRAINEE(new HashSet<>() {{
    add(COURSE_READ);
    add(STUDENT_READ);
  }})
  ;

  private final Set<ApplicationUserPermission> permissions;

  ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
    this.permissions = permissions;
  }

  public Set<ApplicationUserPermission> getPermissions() {
    return permissions;
  }

  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    final Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
        .map(e -> new SimpleGrantedAuthority(e.getPermission()))
        .collect(Collectors.toSet());

    permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

    return permissions;
  }
}
