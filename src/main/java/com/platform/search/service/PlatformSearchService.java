package com.platform.search.service;

import com.platform.search.request.PlatformGarageSearchRequest;
import com.platform.search.response.GenericResponse;

import java.util.List;

public interface PlatformSearchService {

	GenericResponse getPlatformGarageDetails(PlatformGarageSearchRequest request, String brand, String city, String zip, String garageName, List<Integer> serviceType);

//    GenericResponse getGarageByQuery(PlatformGarageSearchRequest request);
}
