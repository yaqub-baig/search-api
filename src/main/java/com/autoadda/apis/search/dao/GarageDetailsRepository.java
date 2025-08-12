package com.autoadda.apis.search.dao;

import com.autoadda.apis.search.entity.GarageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageDetailsRepository extends JpaRepository<GarageDetails, Integer> {

}
