package springstudy.querydsl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {
}
