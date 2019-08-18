package springstudy.restquery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springstudy.restquery.domain.IUserDao;
import springstudy.restquery.infra.UserDao;

@Configuration
public class DaoProvider {

    @Bean
    public IUserDao userDao() {
        return new UserDao();
    }
}
