package dev.appkr.example.api.dto;

import dev.appkr.example.domain.Address;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressListDto {

  private List<Address> data;
  private PageDto page;
}
