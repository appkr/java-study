package dev.appkr.securitydemo.auth;

import static dev.appkr.securitydemo.security.ApplicationUserRole.ADMIN;
import static dev.appkr.securitydemo.security.ApplicationUserRole.ADMINTRAINEE;
import static dev.appkr.securitydemo.security.ApplicationUserRole.STUDENT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository("fake")
public class FakeApplicationUserDao implements ApplicationUserDao {

  private final PasswordEncoder passwordEncoder;

  public FakeApplicationUserDao(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Optional<ApplicationUser> findUserByUsername(String username) {
    return getApplicationUsers().stream()
        .filter(e -> e.getUsername().equals(username))
        .findFirst();
  }

  private List<ApplicationUser> getApplicationUsers() {
    ArrayList<ApplicationUser> applicationUsers = new ArrayList<>();
    applicationUsers.add(new ApplicationUser("anna", passwordEncoder.encode("password"), STUDENT.getGrantedAuthorities(), true));
    applicationUsers.add(new ApplicationUser("linda", passwordEncoder.encode("password"), ADMIN.getGrantedAuthorities(), true));
    applicationUsers.add(new ApplicationUser("tom", passwordEncoder.encode("password"), ADMINTRAINEE.getGrantedAuthorities(), true));

    return applicationUsers;
  }
}
