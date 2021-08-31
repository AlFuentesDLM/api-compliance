package com.mercadolibre.apicompliance.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsersResponseDTO {
    private String name;
    private String terminal;
    private String host;
    private Integer number;
}
