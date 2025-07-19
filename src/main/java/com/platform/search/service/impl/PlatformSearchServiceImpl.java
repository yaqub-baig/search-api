package com.platform.search.service.impl;

import com.platform.search.dao.*;
import com.platform.search.entity.GarageAddress;
import com.platform.search.entity.GarageDetails;
import com.platform.search.entity.GaragePendingActions;
import com.platform.search.entity.ServiceType;
import com.platform.search.request.PlatformGarageSearchRequest;
import com.platform.search.response.GarageSearchResults;
import com.platform.search.response.GenericResponse;
import com.platform.search.service.PlatformSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PlatformSearchServiceImpl implements PlatformSearchService {

    @Autowired
    private GarageDetailsRepository garageRepository;

    @Autowired
    private GarageAddressRepository garageAddressRepository;

    @Autowired
    private PlatformServiceRepository platformServiceRepository;

    @Autowired
    private GaragePendingActionsRepository garagePendingActionsRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Override
    public GenericResponse getPlatformGarageDetails( String brand, String city, String zip, String garageName, List<Integer> serviceType) {
        try {

            List<GarageSearchResults> searchResults = new ArrayList<> ( );
//            List<ServiceType> serviceTypes = serviceTypeRepository.findAllById ( request.getServiceType ( ) );
            List<GaragePendingActions> enabledGarages = garagePendingActionsRepository.findBySearchInd ( "Y" );
            log.info ( " 1  enabledGarages -> " + enabledGarages.size ( ) );
            for (GaragePendingActions actions : enabledGarages) {
                boolean cityCheck = true;
                boolean nameCheck = true;
                boolean zipCheck = true;
                boolean brandCheck = true;
                boolean serviceCheck = true;
                log.info ( " 2  enabledGarages -> " + enabledGarages.size ( ) );
                GarageDetails garageInfo = garageRepository.findById ( actions.getGarageId ( ) ).get ( );
//                log.info ( " 3  enabledGarages -> " + garageInfo.toString ( ) );
                GarageAddress address = garageAddressRepository.findByGarageId ( actions.getGarageId ( ) );
                log.info ( " 4 enabledGarages -> " );
                if ( StringUtils.isNotEmpty ( city ) ) {
                    log.info ( " 5 enabledGarages -> " );
//                    if ( ! address.getCity ( ).equalsIgnoreCase ( city ) ) {
                    if ( ! garageInfo.getCity ( ).equalsIgnoreCase ( city ) ) {
                        cityCheck = false;
                    }
                }

                if ( StringUtils.isNotEmpty ( zip ) ) {
                    log.info ( " 6 enabledGarages -> " );
                    if ( ! address.getPostalCode ( ).equalsIgnoreCase ( zip ) ) {
                        zipCheck = false;
                    }
                }

                if ( StringUtils.isNotEmpty ( garageName ) ) {
                    if ( ! garageInfo.getGarageName ( ).contains ( garageName ) ) {
                        nameCheck = false;
                    }
                }

                if ( StringUtils.isNotEmpty ( brand ) ) {
                    if ( ! garageInfo.getBrands ( ).contains ( brand ) ) {
                        brandCheck = false;
                    }
                }
                if ( serviceType != null && ! serviceType.isEmpty ( ) ) {
                    List<Integer> requestedServiceIds = serviceType;
                    List<Integer> garageServiceIds = garageInfo.getServiceType ( ).stream ( )
                            .map ( ServiceType::getId )
                            .toList ( );

                    serviceCheck = requestedServiceIds.stream ( )
                            .anyMatch ( garageServiceIds::contains );
                }
                if ( nameCheck && cityCheck && zipCheck && brandCheck && serviceCheck ) {
                    searchResults.add ( GarageSearchResults.builder ( )
                            .actions ( actions )
                            .address ( address )
                            .garageDetails ( garageInfo )
                            .build ( ) );
                }


            }
            return GenericResponse.builder ( ).body ( searchResults ).code ( "200" ).message ( "success" ).status ( "success" ).build ( );
        }
        catch ( Exception e ) {
            e.printStackTrace ( );
            log.error ( "ERROR while searching garages" );
        }
        return GenericResponse.builder ( ).status ( "failed" ).message ( "failed" ).code ( "500" ).build ( );
    }

//    @Override
//    public GenericResponse getGarageByQuery(PlatformGarageSearchRequest request) {
//        List<GarageSearchResults> searchResults = new ArrayList<> ( );
//        List<GaragePendingActions> enabledGarages = garagePendingActionsRepository.findBySearchInd ( "Y" );
//        log.info ( " 1  enabledGarages -> " + enabledGarages.size ( ) );
//        for (GaragePendingActions actions : enabledGarages) {
//            boolean cityCheck = true;
//            boolean nameCheck = true;
//            boolean zipCheck = true;
//            log.info ( " 2  enabledGarages -> " + enabledGarages.size ( ) );
//            GarageDetails garageInfo = garageRepository.findById ( actions.getGarageId ( ) ).get ( );
//            log.info ( " 3  enabledGarages -> " + garageInfo.toString ( ) );
//            GarageAddress address = garageAddressRepository.findByGarageId ( actions.getGarageId ( ) );
//            log.info ( " 4 enabledGarages -> " );
//            if ( StringUtils.isNotEmpty ( request.getCity ( ) ) ) {
//                log.info ( " 5 enabledGarages -> " );
//                if ( ! address.getCity ( ).equalsIgnoreCase ( request.getCity ( ) ) ) {
//                    cityCheck = false;
//                }
//            }
//
//            if ( StringUtils.isNotEmpty ( request.getZip ( ) ) ) {
//                log.info ( " 6 enabledGarages -> " );
//                if ( ! address.getPostalCode ( ).equalsIgnoreCase ( request.getZip ( ) ) ) {
//                    zipCheck = false;
//                }
//            }
//
//            if ( StringUtils.isNotEmpty ( request.getGarageName ( ) ) ) {
//                if ( ! garageInfo.getGarageName ( ).contains ( request.getGarageName ( ) ) ) {
//                    nameCheck = false;
//                }
//            }
//
//
//            if ( nameCheck && cityCheck && zipCheck ) {
//                searchResults.add ( GarageSearchResults.builder ( )
//                        .actions ( actions )
//                        .address ( address )
//                        .garageDetails ( garageInfo )
//                        .build ( ) );
//            }
//
//        }
//        return GenericResponse.builder ( ).body ( searchResults ).code ( "200" ).message ( "success" ).status ( "success" ).build ( );
//
//    }
//
//
}
