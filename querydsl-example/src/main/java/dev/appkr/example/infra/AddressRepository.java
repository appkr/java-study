package dev.appkr.example.infra;

import dev.appkr.example.domain.Address;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {

}
