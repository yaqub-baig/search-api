package com.autoadda.apis.search.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "garage_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GarageDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "garage_name")
    private String garageName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "brands")
    private String brands;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "garage_details_service_type",
            joinColumns = @JoinColumn(name = "garage_details_id"),
            inverseJoinColumns = @JoinColumn(name = "service_type_id")
    )
    @Builder.Default
    @JsonIgnoreProperties("garageDetails") // Prevents infinite recursion
    private List<ServiceType> serviceType = new ArrayList<>();

    @Column(name = "city")
    private String city;

    @Column(name = "website")
    private String webSite;

    @Column(name = "account_status")
    private String accountStatus;

    @Column(name = "garage_type")
    private String garageType;

    @Column(name = "chain_id")
    private Integer chainId;

    @Column(name = "insert_tms")
    private Date insertTms;




}
