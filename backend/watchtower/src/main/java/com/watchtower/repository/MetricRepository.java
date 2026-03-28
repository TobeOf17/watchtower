package com.watchtower.repository;

import com.watchtower.model.MetricEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MetricRepository extends JpaRepository<MetricEntry, Long> {

    List<MetricEntry> findTop100ByOrderByTimestampDesc();

    @Query("SELECT COUNT(m) FROM MetricEntry m WHERE m.isSuccess = false")
    Long countFailures();

    @Query("SELECT COUNT(m) FROM MetricEntry m")
    Long countTotal();

    // For MTTR/MTTD calculations
    List<MetricEntry> findAllByOrderByTimestampAsc();

    Optional<MetricEntry> findTopByOrderByTimestampDesc();
}