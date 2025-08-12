package com.service.search.service.impl;

import com.service.search.dao.GarageAddressRepository;
import com.service.search.dao.GarageDetailsRepository;
import com.service.search.dao.GaragePendingActionsRepository;
import com.service.search.dao.ServiceTypeRepository;
import com.service.search.entity.GarageAddress;
import com.service.search.entity.GarageDetails;
import com.service.search.entity.GaragePendingActions;
import com.service.search.entity.ServiceType;
import com.service.search.response.GarageSearchResults;
import com.service.search.response.GenericResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PlatformSearchServiceImplTest {

    @Mock
    private GarageDetailsRepository garageRepository;
    @Mock
    private GarageAddressRepository garageAddressRepository;
    @Mock
    private GaragePendingActionsRepository garagePendingActionsRepository;
    @Mock
    private ServiceTypeRepository serviceTypeRepository;

    @InjectMocks
    private SearchServiceImpl platformSearchService;

    // ================ TEST CASES ================

    @Test
    void getPlatformGarageDetails_withAllValidFilters_shouldReturnMatchingGarages() {
        // Arrange (TC_GS_002)
        GaragePendingActions action = createPendingAction ( 1, "Y" );
        GarageDetails garage = createGarage ( 1, "Speed Garage", "Dubai", "Toyota,Honda",
                List.of ( createServiceType ( 1, "Oil Change" ) ) );
        GarageAddress address = createAddress ( 1, "12345" );

        when ( garagePendingActionsRepository.findBySearchInd ( "Y" ) ).thenReturn ( List.of ( action ) );
        when ( garageRepository.findById ( 1 ) ).thenReturn ( Optional.of ( garage ) );
        when ( garageAddressRepository.findByGarageId ( 1 ) ).thenReturn ( address );

        // Act
        GenericResponse response = platformSearchService.getPlatformGarageDetails (
                "Toyota", "Dubai", "12345", "Speed", List.of ( 1 ) );

        // Assert
        List<GarageSearchResults> results = (List<GarageSearchResults>) response.getBody ( );
        assertThat ( response.getStatus ( ) ).isEqualTo ( "success" );
        assertThat ( results ).hasSize ( 1 );
        assertThat ( results.get ( 0 ).getGarageDetails ( ).getGarageName ( ) ).isEqualTo ( "Speed Garage" );
    }

    @Test
    void getPlatformGarageDetails_withKeywordOnly_shouldReturnPartialMatches() {
        // Arrange (TC_GS_004)
        GaragePendingActions action = createPendingAction ( 1, "Y" );
        GarageDetails garage = createGarage ( 1, "Speed Garage", null, null, null );

        when ( garagePendingActionsRepository.findBySearchInd ( "Y" ) ).thenReturn ( List.of ( action ) );
        when ( garageRepository.findById ( 1 ) ).thenReturn ( Optional.of ( garage ) );
        when ( garageAddressRepository.findByGarageId ( 1 ) ).thenReturn ( new GarageAddress ( ) );

        // Act
        GenericResponse response = platformSearchService.getPlatformGarageDetails (
                null, null, null, "Speed", null );

        // Assert
        List<GarageSearchResults> results = (List<GarageSearchResults>) response.getBody ( );
        assertThat ( results.get ( 0 ).getGarageDetails ( ).getGarageName ( ) ).contains ( "Speed" );
    }

    @Test
    void getPlatformGarageDetails_withNoFilters_shouldReturnAllEnabledGarages() {
        // Arrange (TC_GS_006)
        GaragePendingActions action = createPendingAction ( 1, "Y" );
        when ( garagePendingActionsRepository.findBySearchInd ( "Y" ) ).thenReturn ( List.of ( action ) );
        when ( garageRepository.findById ( 1 ) ).thenReturn ( Optional.of ( new GarageDetails ( ) ) );
        when ( garageAddressRepository.findByGarageId ( 1 ) ).thenReturn ( new GarageAddress ( ) );

        // Act
        GenericResponse response = platformSearchService.getPlatformGarageDetails (
                null, null, null, null, null );

        // Assert
        List<GarageSearchResults> results = (List<GarageSearchResults>) response.getBody ( );
        assertThat ( results ).hasSize ( 1 );
    }

    @Test
    void getPlatformGarageDetails_whenApiFails_shouldReturnErrorResponse() {
        // Arrange (TC_GS_005, TC_GS_008)
        when ( garagePendingActionsRepository.findBySearchInd ( "Y" ) )
                .thenThrow ( new RuntimeException ( "DB Connection Failed" ) );

        // Act
        GenericResponse response = platformSearchService.getPlatformGarageDetails (
                null, null, null, null, null );

        // Assert
        assertThat ( response.getStatus ( ) ).isEqualTo ( "failed" );
        assertThat ( response.getCode ( ) ).isEqualTo ( "500" );
    }

    @Test
    void getPlatformGarageDetails_withInvalidServiceType_shouldIgnoreInvalidTypes() {
        // Arrange (TC_GS_007)
        GaragePendingActions action = createPendingAction ( 1, "Y" );
        GarageDetails garage = createGarage ( 1, "G1", "Dubai", null,
                List.of ( createServiceType ( 1, "Valid" ) ) );

        when ( garagePendingActionsRepository.findBySearchInd ( "Y" ) ).thenReturn ( List.of ( action ) );
        when ( garageRepository.findById ( 1 ) ).thenReturn ( Optional.of ( garage ) );
        when ( garageAddressRepository.findByGarageId ( 1 ) ).thenReturn ( new GarageAddress ( ) );

        // Act - Passing invalid service type (999) along with valid one (1)
        GenericResponse response = platformSearchService.getPlatformGarageDetails (
                null, null, null, null, List.of ( 1, 999 ) );

        // Assert - Should still return the garage since it has serviceType=1
        List<GarageSearchResults> results = (List<GarageSearchResults>) response.getBody ( );
        assertThat ( results ).hasSize ( 1 );
    }

    // ================ HELPER METHODS ================
    private GaragePendingActions createPendingAction(int garageId, String searchInd) {
        GaragePendingActions action = new GaragePendingActions ( );
        action.setGarageId ( garageId );
        action.setSearchInd ( searchInd );
        return action;
    }

    private GarageDetails createGarage(int id, String name, String city, String brands, List<ServiceType> services) {
        return GarageDetails.builder ( )
                .id ( id )
                .garageName ( name )
                .city ( city )
                .brands ( brands )
                .serviceType ( services )
                .build ( );
    }

    private GarageAddress createAddress(int garageId, String zip) {
        GarageAddress address = new GarageAddress ( );
        address.setGarageId ( garageId );
        address.setPostalCode ( zip );
        return address;
    }

    private ServiceType createServiceType(int id, String name) {
        ServiceType serviceType = new ServiceType ( );
        serviceType.setId ( id );
        serviceType.setDsc ( name );
        return serviceType;
    }
}