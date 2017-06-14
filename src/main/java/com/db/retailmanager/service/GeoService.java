package com.db.retailmanager.service;

import com.db.retailmanager.value.distance.DistanceMatrixResponse;
import com.db.retailmanager.value.geocode.GeoCodeResponse;


/**
 * Created by Rajesh on 14-Jun-17.
 */
public interface GeoService {
    GeoCodeResponse getGeoCodeResponse(String address) throws Exception;

    DistanceMatrixResponse getDistanceMatrixResponse(String src, String dest) throws Exception;
}
