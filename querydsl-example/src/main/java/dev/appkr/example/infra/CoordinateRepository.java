package dev.appkr.example.infra;

import dev.appkr.example.domain.Coordinate;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CoordinateRepository extends PagingAndSortingRepository<Coordinate, Long> {

}
