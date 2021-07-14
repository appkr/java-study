package dev.appkr.example.api;

import dev.appkr.example.api.dto.RegionListDto;
import dev.appkr.example.api.dto.RegionSearchParam;
import dev.appkr.example.api.dto.RegionDto;
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
public class RegionApi {

  private final AddressRepository repository;

  @GetMapping(path = "/regions")
  public RegionListDto listRegions(@RequestParam(value = "regionType", required = false, defaultValue = "ROAD") String regionType,
                                   @RequestParam(value = "depth1", required = false) String depth1,
                                   @RequestParam(value = "depth2", required = false) String depth2,
                                   @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

    final RegionSearchParam searchParam = RegionSearchParam.builder()
        .regionType(RegionType.valueOf(regionType))
        .depth1(depth1)
        .depth2(depth2)
        .build();

    Page<? extends RegionDto> all = repository.findAllRegionBy(searchParam, PaginationUtils.getPageable(page, size));

    return RegionListDto.builder()
        .data(all.getContent())
        .page(PaginationUtils.getPageDto(all))
        .build();
  }
}
