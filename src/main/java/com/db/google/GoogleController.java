package com.db.google;

import com.db.retailmanager.value.distance.DistanceMatrixResponse;
import com.db.retailmanager.value.distance.SourceToDestination;
import com.db.retailmanager.value.geocode.GeoCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class GoogleController {
    private static final Logger log = LoggerFactory.getLogger(GoogleController.class);
    private static final String API_KEY = "AIzaSyDuGQkNum1oTDMVi4dYkru5rvCFabOEIa4";//TODO : TO come from application.properties
    public static final String GEOCODEURL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    public static final String DISTANCE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @RequestMapping(value = "/geocode/{address}", method = RequestMethod.GET)
    public GeoCodeResponse getGeoCodeResponse(@PathVariable String address) throws Exception {
        log.debug("geo code address called with "+address);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForObject(GEOCODEURL + address + "&key=" + API_KEY,
                        GeoCodeResponse.class);
    }

    @RequestMapping(value = "/distance/{src}/{dest}", method = RequestMethod.GET)
    public DistanceMatrixResponse getDistanceMatrixResponse(@PathVariable(value="src") String src, @PathVariable(value="dest") String dest) throws Exception {
        log.debug("distance to address called with src "+src + " dest "+ dest);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForObject(DISTANCE_URL +src+"&destinations="+dest+"&mode=walking&language=en-GB"+ "&key=" + API_KEY,
                        DistanceMatrixResponse.class);
    }
}