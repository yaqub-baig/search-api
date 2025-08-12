package com.service.search.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.search.entity.Garage;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Integer> {
}
