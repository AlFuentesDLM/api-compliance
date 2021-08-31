package com.mercadolibre.apicompliance.dtos.request;

import lombok.Data;

@Data
public class ProcessRequestDTO {
    private String name;
    private Integer pid;
    private String username;
    private Integer ppid;
}
