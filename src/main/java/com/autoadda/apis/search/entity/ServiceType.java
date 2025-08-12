package com.autoadda.apis.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "service")
    private String service;

    @Column(name = "dsc")
    private String dsc;

    @ManyToMany(mappedBy = "serviceType", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("serviceType")
    private List<GarageDetails> garageDetails = new ArrayList<>();
}