package com.mercadolibre.apicompliance.controller;

import com.mercadolibre.apicompliance.dtos.request.AuditRequestDTO;
import com.mercadolibre.apicompliance.dtos.response.*;
import com.mercadolibre.apicompliance.service.IAuditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {
    IAuditService auditService;

    public AuditController(IAuditService agentService) {
        this.auditService = agentService;
    }

    @PostMapping()
    private ResponseEntity<AuditCreatedResponseDTO> addNewRecord(@Valid @RequestBody AuditRequestDTO auditRequestDTO,
                                                    HttpServletRequest request){
        return new ResponseEntity<>(auditService.addNewRecord(auditRequestDTO,request.getRemoteAddr()),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    private ResponseEntity<AuditRequestDTO> getAgentRecord(@PathVariable String id){
        return null;
    }

    @GetMapping("/{id}/process")
    private ResponseEntity<List<ProcessResponseDTO>> getListProcessAgent(@PathVariable String id,
                                                                         @RequestParam(required = false) String filter){
        return null;
    }
    @GetMapping("/{id}/users")
    private ResponseEntity<List<UsersResponseDTO>> getUsersListAgent(@PathVariable String id){
        return null;
    }

}
