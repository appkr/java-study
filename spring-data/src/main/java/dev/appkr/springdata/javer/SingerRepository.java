package dev.appkr.springdata.javer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SingerRepository extends JpaRepository<Singer, Integer> {
}
