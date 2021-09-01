package com.mercadolibre.apicompliance.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String error;
    private Integer status;
    private String message;
}
