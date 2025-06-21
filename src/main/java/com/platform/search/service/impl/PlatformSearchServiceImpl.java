package com.platform.search.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.search.dao.GarageAddressRepository;
import com.platform.search.dao.GaragePendingActionsRepository;
import com.platform.search.dao.GarageRepository;
import com.platform.search.dao.PlatformServiceRepository;
import com.platform.search.entity.Garage;
import com.platform.search.entity.GarageAddress;
import com.platform.search.entity.GaragePendingActions;
import com.platform.search.request.PlatformGarageSearchRequest;
import com.platform.search.response.GarageSearchResults;
import com.platform.search.response.GenericResponse;
import com.platform.search.service.PlatformSearchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlatformSearchServiceImpl implements PlatformSearchService{
	
	@Autowired
	private GarageRepository garageRepository;
	
	@Autowired
	private GarageAddressRepository garageAddressRepository;
	
	@Autowired
	private PlatformServiceRepository platformServiceRepository;
	
	@Autowired
	private GaragePendingActionsRepository garagePendingActionsRepository;
	
	@Override
	public GenericResponse getPlatformGarageDetails(PlatformGarageSearchRequest request) {
		try {
			
			List<GarageSearchResults> searchResults = new ArrayList<>();
			List<GaragePendingActions> enabledGarages = garagePendingActionsRepository.findBySearchInd("Y");
			for (GaragePendingActions actions : enabledGarages) {
				boolean cityCheck = true;
				boolean nameCheck = true;
				boolean zipCheck = true;
				Garage garageInfo = garageRepository.findById(actions.getGarageId()).get();
				GarageAddress address = garageAddressRepository.findByGarageId(actions.getGarageId());
				if(StringUtils.isNotEmpty(request.getCity())) {
					if(!address.getCity().equalsIgnoreCase(request.getCity())) {
						cityCheck = false;
					}
				}
				
				if(StringUtils.isNotEmpty(request.getZip())) {
					if(!address.getPostalCode().equalsIgnoreCase(request.getZip())) {
						zipCheck = false;
					}
				}
				
				if(StringUtils.isNotEmpty(request.getGarageName())) {
					if(!garageInfo.getGarageName().contains(request.getGarageName())) {
						nameCheck = false;
					}
				}
				if(nameCheck && cityCheck && zipCheck) {
					searchResults.add(GarageSearchResults.builder()
							.actions(actions)
							.address(address)
							.garageDetails(garageInfo)
							.build());
				}
				
			}
			return GenericResponse.builder().body(searchResults).code("200").message("success").status("success").build();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR while searching garages");
		}
		return GenericResponse.builder().status("failed").message("failed").code("500").build();
	}

}
