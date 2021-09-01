package com.mercadolibre.apicompliance.service;

import com.mercadolibre.apicompliance.dtos.request.AuditRequestDTO;
import com.mercadolibre.apicompliance.dtos.response.*;

import java.util.List;

public interface IAuditService {
    AuditCreatedResponseDTO addNewRecord(AuditRequestDTO agentRequestDTO, String ip);
    AuditResponseDTO getAuditById(Long id);
    List<AuditResponseDTO>  getAuditsByIp(String ip);
    List<ProcessResponseDTO> getProcessByAuditId(Long id,String filter);
    List<UsersResponseDTO> getUsersByAuditId(Long id);
}
