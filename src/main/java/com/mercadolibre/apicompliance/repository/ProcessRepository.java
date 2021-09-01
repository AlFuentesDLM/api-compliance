package com.mercadolibre.apicompliance.repository;

import com.mercadolibre.apicompliance.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessRepository extends JpaRepository<Process,Long> {
    List<Process> getAllByAuditId(Long id);
}
