package com.autoadda.apis.search.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autoadda.apis.search.entity.PlatformService;

@Repository
public interface ServicesRepository extends JpaRepository<PlatformService, Integer> {

	List<PlatformService> findByIdIn(List<Integer> serviceIds);

}
