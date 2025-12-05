package com.nocountry.crm.integration.email.service;

import com.nocountry.crm.integration.email.entity.HistoryTrackerData;
import com.nocountry.crm.integration.email.repository.HistoryTrackerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class HistoryTrackerService {
    private final HistoryTrackerRepository repository;

    public void saveInitialHistoryId(BigInteger historyId) {
        HistoryTrackerData data = repository.findById(1L).orElse(new HistoryTrackerData());
        data.setHistoryId(historyId);
        repository.save(data);
    }

    public synchronized BigInteger getLastHistoryId() {
        return repository.findById(1L)
                .map(HistoryTrackerData::getHistoryId)
                .orElseThrow(() -> new RuntimeException("No history stored! Set it with the initial watch() response."));
    }

    public synchronized void updateHistoryId(BigInteger newHistoryId) {
        HistoryTrackerData state = repository.findById(1L).orElse(new HistoryTrackerData());
        state.setHistoryId(newHistoryId);
        repository.save(state);
    }
}
