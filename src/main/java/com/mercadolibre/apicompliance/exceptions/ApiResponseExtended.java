package com.mercadolibre.apicompliance.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ApiResponseExtended {
    private Integer status;
    private String error;
    private Map<String, String> message;
}
