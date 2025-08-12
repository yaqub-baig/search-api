package com.autoadda.apis.search.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.autoadda.apis.search.entity.GarageAddress;
import com.autoadda.apis.search.entity.GarageDetails;
import com.autoadda.apis.search.entity.GaragePendingActions;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class GarageSearchResults {
    
    @JsonIgnoreProperties({"serviceType"}) // Prevent infinite recursion
    private GarageDetails garageDetails;
    
    private GarageAddress address;
    private GaragePendingActions actions;
}
