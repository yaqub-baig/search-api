package com.platform.search.response;

import com.platform.search.entity.Garage;
import com.platform.search.entity.GarageAddress;
import com.platform.search.entity.GaragePendingActions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GarageSearchResults {
	
	private Garage garageDetails;
	private GarageAddress address;
	private GaragePendingActions actions;

}
