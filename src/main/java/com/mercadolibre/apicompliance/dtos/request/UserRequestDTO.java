package com.mercadolibre.apicompliance.dtos.request;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String terminal;
    private String host;
    private Integer pid;
}
