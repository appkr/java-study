package dev.appkr.example.api;

import dev.appkr.example.api.dto.AddressListDto;
import dev.appkr.example.api.dto.AddressSearchParam;
import dev.appkr.example.domain.Address;
import dev.appkr.example.domain.RegionType;
import dev.appkr.example.infra.AddressRepository;
import dev.appkr.example.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressApi {

  private final AddressRepository repository;

  @GetMapping(path = "/addresses")
  public AddressListDto listAddress(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

    final Page<Address> all = repository.findAll(PaginationUtils.getPageable(page, size));

    return AddressListDto.builder()
        .data(all.getContent())
        .page(PaginationUtils.getPageDto(all))
        .build();
  }

  @GetMapping(path = "/search")
  public AddressListDto searchAddress(@RequestParam(value = "regionType", required = false) String regionType,
                                      @RequestParam(value = "depth1", required = false) String depth1,
                                      @RequestParam(value = "depth2", required = false) String depth2,
                                      @RequestParam(value = "depth3", required = false) String depth3,
                                      @RequestParam(value = "depth4", required = false) String depth4,
                                      @RequestParam(value = "number", required = false) String number,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

    final AddressSearchParam searchParam = AddressSearchParam.builder()
        .regionType(RegionType.valueOf(regionType))
        .depth1(depth1)
        .depth2(depth2)
        .depth3(depth3)
        .depth4(depth4)
        .number(number)
        .build();

    final Page<Address> all = repository.findAll(searchParam, PaginationUtils.getPageable(page, size));

    return AddressListDto.builder()
        .data(all.getContent())
        .page(PaginationUtils.getPageDto(all))
        .build();
  }
}
