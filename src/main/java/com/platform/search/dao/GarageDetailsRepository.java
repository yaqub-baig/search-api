package com.platform.search.dao;

import com.platform.search.entity.GarageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageDetailsRepository extends JpaRepository<GarageDetails, Integer> {

}
