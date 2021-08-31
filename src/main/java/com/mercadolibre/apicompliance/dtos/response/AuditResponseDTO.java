package com.mercadolibre.apicompliance.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuditResponseDTO {
    private String os_name;
    private String os_version;
    private String architecture;
    private Integer cpu_logical_cores;
    private Integer cpu_physical_cores;
    private String brand;
    private Long auditTime;
}