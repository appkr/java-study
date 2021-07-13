package dev.appkr.example.infra;

import dev.appkr.example.api.dto.AddressSearchParam;
import dev.appkr.example.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressRepositoryCustom {

  Page<Address> findAll(AddressSearchParam searchParam, Pageable pageable);
}
