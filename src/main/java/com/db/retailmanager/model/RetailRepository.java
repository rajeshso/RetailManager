package com.db.retailmanager.model;

import com.db.retailmanager.value.*;
import com.db.retailmanager.value.distance.DistanceMatrixResponse;
import com.db.retailmanager.value.distance.Elements;
import com.db.retailmanager.value.distance.Rows;
import com.db.retailmanager.value.distance.SourceToDestination;
import com.db.retailmanager.value.geocode.GeoCodeResponse;
import com.db.retailmanager.value.geocode.Geometry;
import com.db.retailmanager.value.geocode.Location;
import com.db.retailmanager.value.geocode.Results;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Rajesh on 15-May-17.
 */
public final class RetailRepository {

    private static final RetailRepository INSTANCE = new RetailRepository();
    public static final String GEOCODE_SERVICE_URL = "http://localhost:8090/geocode/"; //TODO: from application.properties
    public static final String DISTANCE_SERVICE_URL = "http://localhost:8090/distance/"; //TODO: from application.properties
    private static final int WITHIN_DISTANCE = 10000; //TODO: from application.properties
    protected Map<Shop,Location> shops = new ConcurrentHashMap<>(2);

    private RetailRepository(){}
    public static RetailRepository getInstance() {
        return INSTANCE;
    }

    public ShopStatus addShop(Shop shop) {
        Location location = findLocation(shop);
        ShopStatus shopStatus = buildShopStatus(shop);
        shops.put(shop, location);
        return shopStatus;
    }

    protected ShopStatus buildShopStatus(Shop shop) {
        ShopStatus shopStatus = new ShopStatus(shops.keySet().stream().filter(x-> x.getShopName()==shop.getShopName()).findAny().orElse(shop));
        if (shops.containsKey(shop)) {
            shopStatus.setMessage("Replaced the shop");
        }else {
            shopStatus.setMessage("Added the shop");
        }
        return shopStatus;
    }

    public NearestShops getNearestShops(CustomerLocation customerLocation) {
        Map<Shop,Location> allShops = new HashMap<>(shops);
        SourceToDestination srcDest = getSourceToDestination(customerLocation, allShops);
        DistanceMatrixResponse distanceMatrixResponse = getDistanceMatrixResponse(srcDest);
        List<Integer> distance = getDistance(distanceMatrixResponse);
        Map<Shop, Integer> shopVsDistance = getShopDistanceMap(allShops, distance);
        NearestShops result = buildNearestShopsResponse(shopVsDistance);
        return result;
    }

    protected Map<Shop, Integer> getShopDistanceMap(Map<Shop, Location> allShops, List<Integer> distance) {
        Map<Shop, Integer> shopVsDistance = new HashMap<>(distance.size());
        Iterator<Shop> shopIterator = allShops.keySet().iterator();
        int i=0;
        while (shopIterator.hasNext()) {
            Shop shop = shopIterator.next();
            if (distance.get(i)<=WITHIN_DISTANCE)
                shopVsDistance.put(shop, distance.get(i));
            i++;
        }
        return shopVsDistance;
    }

    protected DistanceMatrixResponse getDistanceMatrixResponse(SourceToDestination srcDest) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = DISTANCE_SERVICE_URL+ srcDest.getSource()+"/"+srcDest.getDestination();
        return restTemplate.getForObject(uri, DistanceMatrixResponse.class, srcDest);
    }

    protected List<Integer> getDistance(DistanceMatrixResponse distanceMatrixResponse) {
        List<Rows> rowsLit = Arrays.asList(distanceMatrixResponse.getRows());
        List<Elements> elements = rowsLit.stream().map(rows1 -> rows1.getElements()).flatMap(elements1 -> Arrays.stream(elements1)).collect(Collectors.toList());
        return elements.stream().map(index -> index.getDistance().getValue()).collect(Collectors.toList());
    }

    protected NearestShops buildNearestShopsResponse(Map<Shop, Integer> shopVsDistance) {
        List<Shop> nearestShopList = shopVsDistance.entrySet().stream()
                .sorted(Map.Entry.<Shop, Integer>comparingByValue())
                .limit(10)
                .map(x-> x.getKey()).collect(Collectors.toList());
        Shop[] nearestShops = new Shop[nearestShopList.size()];
        nearestShops = nearestShopList.toArray(nearestShops);
        NearestShops result = new NearestShops();
        result.setShops(nearestShops);
        result.setMessage("OK");
        return result;
    }

    protected SourceToDestination getSourceToDestination(CustomerLocation customerLocation, Map<Shop, Location> allShops) {
        SourceToDestination srcDest = new SourceToDestination();
        allShops.values().stream().forEach(location -> srcDest.addDestination(location.getLat(),location.getLng()));
        srcDest.addSource(customerLocation.getLat(),customerLocation.getLng());
        return srcDest;
    }

    protected Location findLocation(Shop shop) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append(shop.getShopAddressNumber());//1600
        addressBuilder.append("+");
        addressBuilder.append(shop.getAddressLine1().replaceAll(" ","+"));//Amphitheatre+Parkway,+Mountain+View
        addressBuilder.append("+");
        addressBuilder.append(shop.getCity().replaceAll(" ","+"));//+CA
        String address = addressBuilder.toString();
        String uri = GEOCODE_SERVICE_URL +address;
        GeoCodeResponse geoCodeResponse = restTemplate.getForObject(uri, GeoCodeResponse.class);
        Results results[] = geoCodeResponse.getResults();
        Results results1 = results[0];
        Geometry geometry = results1.getGeometry();
        Location location = geometry.getLocation();
        return location;
    }
}
