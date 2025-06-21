package com.platform.search.service;

import com.platform.search.request.PlatformGarageSearchRequest;
import com.platform.search.response.GenericResponse;

public interface PlatformSearchService {

	GenericResponse getPlatformGarageDetails(PlatformGarageSearchRequest request);

}
