package dev.appkr.example.infra;

import dev.appkr.example.api.dto.AddressSearchParam;
import dev.appkr.example.api.dto.RegionSearchParam;
import dev.appkr.example.domain.Address;
import dev.appkr.example.api.dto.RegionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressRepositoryCustom {

  Page<Address> findAllAddressBy(AddressSearchParam searchParam, Pageable pageable);
  Page<? extends RegionDto> findAllRegionBy(RegionSearchParam searchParam, Pageable pageable);
}
