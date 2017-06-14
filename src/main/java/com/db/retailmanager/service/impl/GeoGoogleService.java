package com.db.retailmanager.service.impl;

import com.db.retailmanager.service.GeoService;
import com.db.retailmanager.value.distance.DistanceMatrixResponse;
import com.db.retailmanager.value.geocode.GeoCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoGoogleService implements GeoService {
    private static final Logger log = LoggerFactory.getLogger(GeoGoogleService.class);
    @Value("${geo.code.url}")
    public String geocodeurl;
    @Value("${distance.url}")
    public String distanceUrl;
    @Value("${api.key}")
    private String apiKey;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public GeoCodeResponse getGeoCodeResponse(String address) throws Exception {
        log.debug("geo code address called with " + address);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForObject(geocodeurl + address + "&key=" + apiKey,
                        GeoCodeResponse.class);
    }

    @Override
    public DistanceMatrixResponse getDistanceMatrixResponse(String src, String dest) throws Exception {
        log.debug("distance to address called with src " + src + " dest " + dest);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForObject(distanceUrl + src + "&destinations=" + dest + "&mode=walking&language=en-GB" + "&key=" + apiKey,
                        DistanceMatrixResponse.class);
    }
}