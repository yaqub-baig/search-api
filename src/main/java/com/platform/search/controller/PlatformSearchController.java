package com.platform.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.search.request.PlatformGarageSearchRequest;
import com.platform.search.response.GenericResponse;
import com.platform.search.service.PlatformSearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/search")
public class PlatformSearchController {
	
	@Autowired
	private PlatformSearchService platformSearchService;
	
	@Operation(summary = "Get a welcome message")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "If service is running fine, shows welcome message", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = GenericResponse .class)) }),
	  @ApiResponse(responseCode = "404", description = "Welcome api not found", 
	    content = @Content) })
	@GetMapping("welcome")
	public GenericResponse getGreetingMessage() {
		return GenericResponse.builder().status("sucess").code("200").message("success").body("Welcome to platform search service..!").build();
	}
	
	@Operation(summary = "GET Garages based on criteria like name or city or zip")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "If service is running fine, shows success message", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = GenericResponse .class)) }),
	  @ApiResponse(responseCode = "404", description = "Search api not found", 
	    content = @Content) })
	@GetMapping("garage")
	public GenericResponse getGarageServiceDetails(PlatformGarageSearchRequest request) {
		GenericResponse response = null;
		try {
			response = platformSearchService.getPlatformGarageDetails(request);
			return response;
		} catch (Exception e) {
			log.error("ERROR while update garage profile details -> "+e.getMessage());
			return GenericResponse.builder().status("failed").code("500").message(e.getMessage()).build();
		}
	}

}
