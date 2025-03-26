package com.code.bank.services.interfaces;

import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.models.Alert;
import com.code.bank.models.enums.AlertStatus;

import java.util.List;

public interface AlertService {
    List<Alert> findAlertByStatus(AlertStatus alertStatus);
    Alert resolveAlert(Long alertId) throws DataNotFoundException;
}
