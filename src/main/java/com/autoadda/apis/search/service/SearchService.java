package com.autoadda.apis.search.service;

import com.autoadda.apis.search.response.GenericResponse;

import java.util.List;

public interface SearchService {

	GenericResponse getPlatformGarageDetails( String brand, String city, String zip, String garageName, List<Integer> serviceType);


}
