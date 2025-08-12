package com.service.search.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.search.entity.GarageAddress;

@Repository
public interface GarageAddressRepository extends JpaRepository<GarageAddress, Integer> {

	GarageAddress findByGarageId(Integer garageId);
}
