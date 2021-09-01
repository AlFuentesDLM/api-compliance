package com.mercadolibre.apicompliance.service.impl;

import com.mercadolibre.apicompliance.dtos.request.AuditRequestDTO;
import com.mercadolibre.apicompliance.dtos.request.ProcessRequestDTO;
import com.mercadolibre.apicompliance.dtos.request.UserRequestDTO;
import com.mercadolibre.apicompliance.dtos.response.*;
import com.mercadolibre.apicompliance.exceptions.ApiException;
import com.mercadolibre.apicompliance.model.Audit;
import com.mercadolibre.apicompliance.model.Process;
import com.mercadolibre.apicompliance.model.Users;
import com.mercadolibre.apicompliance.repository.AuditRepository;
import com.mercadolibre.apicompliance.repository.ProcessRepository;
import com.mercadolibre.apicompliance.repository.UsersRepository;
import com.mercadolibre.apicompliance.service.IAuditService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuditService implements IAuditService {
    AuditRepository auditRepository;
    ProcessRepository processRepository;
    UsersRepository usersRepository;

    public AuditService(AuditRepository auditRepository, ProcessRepository processRepository, UsersRepository usersRepository) {
        this.auditRepository = auditRepository;
        this.processRepository = processRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public AuditCreatedResponseDTO addNewRecord(AuditRequestDTO auditRequestDTO, String ip) {
        Audit audit = mapAgentDto(auditRequestDTO, ip);
        Audit result = auditRepository.save(audit);
        return new AuditCreatedResponseDTO(result.getId());
    }

    @Override
    public AuditResponseDTO getAuditById(Long id) {
        Audit audit = auditRepository.getAuditById(id);
        if (Objects.isNull(audit)) throw new ApiException("not_found", 404, "Audit not found");
        return mapModelToDTO(audit);
    }

    @Override
    public List<AuditResponseDTO> getAuditsByIp(String ip) {
        List<Audit> audits = auditRepository.getAgentsByIp(ip);
        if (audits.isEmpty()) throw new ApiException("not_found", 404, "There's no audit with this ip address");
        return mapListModelToListDTO(audits);
    }

    @Override
    public List<ProcessResponseDTO> getProcessByAuditId(Long id, String filter) {
        List<Process> processList = processRepository.getAllByAuditId(id);
        if (processList.isEmpty()) throw new ApiException("not_found", 404, "There's no process with this audit id");
        return mapProcessToDTO(processList);
    }

    @Override
    public List<UsersResponseDTO> getUsersByAuditId(Long id) {
        List<Users> usersList = usersRepository.getAllByAuditId(id);
        if (usersList.isEmpty()) throw new ApiException("not_found", 404, "There's no users with this audit id");
        return mapUsersToDTO(usersList);
    }

    private List<UsersResponseDTO> mapUsersToDTO(List<Users> usersList) {
        List<UsersResponseDTO> usersResponseDTOS = new ArrayList<>();
        for (Users users : usersList) {
            usersResponseDTOS.add(new UsersResponseDTO(users.getName(),users.getTerminal()
                    ,users.getHost(),users.getPid()));
        }
        return usersResponseDTOS;
    }


    private List<ProcessResponseDTO> mapProcessToDTO(List<Process> processList) {
        List<ProcessResponseDTO> processResponseDTOS = new ArrayList<>();
        for (Process process : processList) {
            processResponseDTOS.add(new ProcessResponseDTO(process.getUsername(),
                    process.getPid(), process.getPpid(), process.getName()));
        }
        return processResponseDTOS;
    }


    private List<AuditResponseDTO> mapListModelToListDTO(List<Audit> audits) {
        List<AuditResponseDTO> list = new ArrayList<>();
        for (Audit audit : audits) {
            list.add(mapModelToDTO(audit));
        }
        return list;
    }

    private AuditResponseDTO mapModelToDTO(Audit audit) {
        return new AuditResponseDTO(audit.getOsName(), audit.getOsVersion(),
                audit.getArchitecture(), audit.getLogicalCores(), audit.getPhysicalCores(),
                audit.getBrand(), audit.getTimestamp());
    }

    private Audit mapAgentDto(AuditRequestDTO auditRequestDTO, String ip) {
        Audit audit = new Audit();
        audit.setOsName(auditRequestDTO.getOs_name());
        audit.setIp(ip);
        audit.setOsVersion(auditRequestDTO.getOs_version());
        audit.setArchitecture(auditRequestDTO.getArchitecture());
        audit.setLogicalCores(auditRequestDTO.getCpu_logical_cores());
        audit.setPhysicalCores(auditRequestDTO.getCpu_physical_cores());
        audit.setBrand(auditRequestDTO.getBrand());
        audit.setProcessList(mapProcessDto(auditRequestDTO.getProcess(), audit));
        audit.setUsersList(mapUsers(auditRequestDTO.getUsers(), audit));
        audit.setTimestamp(auditRequestDTO.getTimestamp());
        return audit;
    }

    private List<Users> mapUsers(List<UserRequestDTO> requestDTO, Audit audit) {
        List<Users> usersList = new ArrayList<>();
        for (UserRequestDTO userRequestDTO : requestDTO) {
            Users user = new Users();
            user.setAudit(audit);
            user.setName(userRequestDTO.getName());
            user.setPid(userRequestDTO.getPid());
            user.setHost(userRequestDTO.getHost());
            user.setTerminal(userRequestDTO.getTerminal());
            usersList.add(user);
        }
        return usersList;
    }

    private List<Process> mapProcessDto(List<ProcessRequestDTO> requestDTOS, Audit audit) {
        List<Process> processList = new ArrayList<>();
        for (ProcessRequestDTO processRequestDTO : requestDTOS) {
            Process process = new Process();
            process.setAudit(audit);
            process.setName(processRequestDTO.getName());
            process.setPid(processRequestDTO.getPid());
            process.setPpid(processRequestDTO.getPpid());
            process.setUsername(processRequestDTO.getUsername());
            processList.add(process);
        }
        return processList;
    }

}
