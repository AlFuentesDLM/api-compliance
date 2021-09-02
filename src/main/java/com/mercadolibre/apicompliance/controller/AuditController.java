package com.mercadolibre.apicompliance.controller;

import com.mercadolibre.apicompliance.dtos.request.AuditRequestDTO;
import com.mercadolibre.apicompliance.dtos.response.*;
import com.mercadolibre.apicompliance.service.IAuditService;
import com.mercadolibre.apicompliance.utils.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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
                                                    HttpServletRequest request) throws IOException {
        FileUtils.saveFile(auditRequestDTO,request.getRemoteAddr()+"-"+auditRequestDTO.getTimestamp().toString());
        return new ResponseEntity<>(auditService.addNewRecord(auditRequestDTO,request.getRemoteAddr()),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    private ResponseEntity<AuditResponseDTO> getAgentRecord(@PathVariable Long id){
        return new ResponseEntity<>(auditService.getAuditById(id),HttpStatus.OK);
    }

    @GetMapping("/{id}/process")
    private ResponseEntity<List<ProcessResponseDTO>> getListProcessAgent(@PathVariable Long id,
                                                                         @RequestParam(required = false) String filter){
        return new ResponseEntity<>(auditService.getProcessByAuditId(id,filter),HttpStatus.OK);
    }
    @GetMapping("/{id}/users")
    private ResponseEntity<List<UsersResponseDTO>> getUsersListAgent(@PathVariable Long id){
        return new ResponseEntity<>(auditService.getUsersByAuditId(id),HttpStatus.OK);
    }

    @GetMapping()
    private  ResponseEntity<List<AuditResponseDTO>> getAuditsByIp(@RequestParam(name = "ip") String ip){
        return new ResponseEntity<>(auditService.getAuditsByIp(ip),HttpStatus.OK);
    }

}
