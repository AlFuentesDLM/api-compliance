package com.mercadolibre.apicompliance.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Users  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String host;
    private String terminal;
    private Integer pid;
    @ManyToOne
    private Audit audit;
}
