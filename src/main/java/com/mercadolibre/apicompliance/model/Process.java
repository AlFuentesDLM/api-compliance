package com.mercadolibre.apicompliance.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private Integer pid;
    private Integer ppid;
    @ManyToOne
    private Audit audit;
  }
