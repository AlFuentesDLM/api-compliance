package com.mercadolibre.apicompliance.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.apicompliance.dtos.request.AuditRequestDTO;
import com.mercadolibre.apicompliance.exceptions.ApiException;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static void saveFile(AuditRequestDTO auditRequestDTO, String filename) {
        try {
            File file = new File("data/"+filename+".json");
            file.createNewFile();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file,auditRequestDTO);
        } catch (JsonGenerationException e) {
            throw new ApiException("bad_request",404,"something went wrong with the file saving");
        } catch (JsonMappingException e) {
            throw new ApiException("bad_request",404,"something went wrong with the file saving");
        } catch (IOException e) {
            throw new ApiException("bad_request",404,"something went wrong with the file saving");
        }
    }
}
