package com.autoadda.apis.search.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autoadda.apis.search.entity.GarageSubscription;

@Repository
public interface GarageSubscriptionRepository extends JpaRepository<GarageSubscription, Integer> {
	
	GarageSubscription findByGarageId(Integer garageId);
}
