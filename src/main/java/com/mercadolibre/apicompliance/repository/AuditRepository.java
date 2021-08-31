package com.mercadolibre.apicompliance.repository;

import com.mercadolibre.apicompliance.model.Audit;
import com.mercadolibre.apicompliance.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AuditRepository extends JpaRepository<Audit,Integer> {
    List<Audit> getAgentsByIp(String ip);
}
