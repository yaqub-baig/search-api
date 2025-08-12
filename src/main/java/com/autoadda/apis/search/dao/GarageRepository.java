package com.autoadda.apis.search.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autoadda.apis.search.entity.Garage;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Integer> {
}
