package com.mercadolibre.apicompliance.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessResponseDTO {
    private String username;
    private Integer pid;
    private Integer ppid;
    private String name;
}
