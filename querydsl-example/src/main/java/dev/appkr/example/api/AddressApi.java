package dev.appkr.example.api;

import dev.appkr.example.api.dto.AddressListDto;
import dev.appkr.example.domain.Address;
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
}
