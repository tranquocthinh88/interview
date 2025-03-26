package com.code.bank.services.impls;

import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.models.Alert;
import com.code.bank.models.enums.AlertStatus;
import com.code.bank.repositories.AlertRepository;
import com.code.bank.services.interfaces.AlertService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;

    public AlertServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public List<Alert> findAlertByStatus(AlertStatus alertStatus) {
        return alertRepository.findByAlertStatus(alertStatus);
    }

    @Override
    public Alert resolveAlert(Long alertId) throws DataNotFoundException {
        Alert alert = alertRepository.findById(alertId).orElseThrow(() -> new DataNotFoundException("Alert not found"));
        alert.setAlertStatus(AlertStatus.RESOLVED);
        return alertRepository.save(alert);
    }
}
