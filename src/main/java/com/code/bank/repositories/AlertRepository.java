package com.code.bank.repositories;

import com.code.bank.models.Alert;
import com.code.bank.models.enums.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByAlertStatus(AlertStatus alertStatus);
}