package com.mercadolibre.apicompliance.repository;

import com.mercadolibre.apicompliance.model.Process;
import com.mercadolibre.apicompliance.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,Long> {
    List<Users> getAllByAuditId(Long id);

}
