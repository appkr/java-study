package springstudy.querydsl;

import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepositoryCustom {

    Optional<Customer> findByName(@Param("name") String name);
}
