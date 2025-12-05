package com.nocountry.crm.integration.email.repository;

import com.nocountry.crm.integration.email.entity.HistoryTrackerData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryTrackerRepository extends JpaRepository<HistoryTrackerData, Long> {
}
