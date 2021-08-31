package com.mercadolibre.apicompliance.dtos.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Validated
public class AuditRequestDTO {
    @NotEmpty
    private List<UserRequestDTO> users;
    @NotEmpty
    private List<ProcessRequestDTO> process;
    @NotNull
    private String os_name;
    @NotNull
    private String os_version;
    @NotNull
    private String architecture;
    @NotNull
    private Integer cpu_logical_cores;
    @NotNull
    private Integer cpu_physical_cores;
    @NotNull
    private String brand;
    @NotNull
    private Long timestamp;
}
