package com.mercadolibre.apicompliance.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String osName;
    private String osVersion;

    @OneToMany(cascade = CascadeType.ALL,fetch =  FetchType.LAZY, mappedBy="audit")
    private List<Process> processList;
    @OneToMany(cascade = CascadeType.ALL,fetch =  FetchType.LAZY,mappedBy="audit")
    private List<Users> usersList;
    private String ip;
    private String architecture;
    private Integer logicalCores;
    private Integer physicalCores;
    private String brand;
    private Long timestamp;
}
