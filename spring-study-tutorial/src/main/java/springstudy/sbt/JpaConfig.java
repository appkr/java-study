package springstudy.sbt;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("springstudy.sbt")
@EntityScan("springstudy.sbt")
public class JpaConfig {
}
