package com.service.search.controller;

import com.service.search.response.GenericResponse;
import com.service.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService platformSearchService;

    @Operation(summary = "Get a welcome message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If service is running fine, shows welcome message",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenericResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Welcome api not found",
                    content = @Content)})
    @GetMapping("welcome")
    public GenericResponse getGreetingMessage() {
        return GenericResponse.builder ( ).status ( "sucess" ).code ( "200" ).message ( "success" ).body ( "Welcome to platform search service..!" ).build ( );
    }

    @Operation(summary = "GET Garages based on criteria like name or city or zip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If service is running fine, shows success message",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenericResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Search api not found",
                    content = @Content)})
    @GetMapping("garage")
    public GenericResponse getGarageServiceDetails(@RequestParam(required = false) String brand, @RequestParam(required = false) String city, @RequestParam(required = false) String zip, @RequestParam(required = false) String garageName, @RequestParam(required = false) List<Integer> serviceType) {
        GenericResponse response = null;
        try {
            response = platformSearchService.getPlatformGarageDetails ( brand, city, zip, garageName, serviceType );
            return response;
        }
        catch ( Exception e ) {
            log.error ( "ERROR while update garage profile details -> " + e.getMessage ( ) );
            return GenericResponse.builder ( ).status ( "failed" ).code ( "500" ).message ( e.getMessage ( ) ).build ( );
        }
    }

}
