package com.db.retailmanager.model;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import com.db.retailmanager.value.CustomerLocation;
import com.db.retailmanager.value.NearestShops;
import com.db.retailmanager.value.Shop;
import com.db.retailmanager.value.ShopStatus;
import com.db.retailmanager.value.distance.*;
import com.db.retailmanager.value.geocode.Location;
import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rajesh on 16-May-17.
 */
public class RetailRepositoryTest {
    @Test
    public void givenCustomerLocationAndShopsThenBuildSrcDestination() {
        CustomerLocation myLocation = getCustomerLocation();
        Shop shop = getShop();
        Location location = getLocation();
        Map<Shop,Location> shopLocationMap = new HashMap<>(1);
        shopLocationMap.put(shop,location);
        RetailRepository retailRepository = RetailRepository.getInstance();
        SourceToDestination sourceToDestination = retailRepository.getSourceToDestination(myLocation, shopLocationMap);
        sourceToDestination.getSource();//51.53664141970851,0.05087131970849797
        sourceToDestination.getDestination(); //51.53664141970851,0.05087131970849797
        assertThat(sourceToDestination.getSource()).isEqualTo("51.53664141970851,0.05087131970849797");
        assertThat(sourceToDestination.getDestination()).isEqualTo("51.53664141970851,0.05087131970849797");
    }

    @Test
    public void givenShopsAndDistanceThenArrangeShopsByNearestDistance() {
        Shop shop1 = new Shop();
        shop1.setShopName("Apple Store");

        Shop shop2 = new Shop();
        shop2.setShopName("Samsung Store");

        Shop shop3 = new Shop();
        shop3.setShopName("Motorola Store");

        Map<Shop, Integer> unSortedShops = new HashMap<>(3);
        unSortedShops.put(shop1,10);
        unSortedShops.put(shop2,5);
        unSortedShops.put(shop3,9);

        RetailRepository retailRepository = RetailRepository.getInstance();
        NearestShops nearestShops = retailRepository.buildNearestShopsResponse(unSortedShops);
        Shop[] shops = nearestShops.getShops();
        assertThat(shops).containsExactly(shop2, shop3, shop1);
    }

    @Test
    public void givenAGoogleResponseThenProduceAListOfDistances() {
        DistanceMatrixResponse distanceMatrixResponse = new DistanceMatrixResponse();
        Elements elements1 = new Elements();
        Elements elements2 = new Elements();
        Elements elements3 = new Elements();
        Distance distance1 = new Distance();
        Distance distance2 = new Distance();
        Distance distance3 = new Distance();
        distance1.setValue(100);
        distance2.setValue(50);
        distance3.setValue(150);
        elements1.setDistance(distance1);
        elements2.setDistance(distance2);
        elements3.setDistance(distance3);
        Elements[] elements = {elements1,elements2,elements3};
        Rows row = new Rows();
        row.setElements(elements);
        Rows[] rows = {row};
        distanceMatrixResponse.setRows(rows);

        RetailRepository retailRepository = RetailRepository.getInstance();
        List<Integer> distance = retailRepository.getDistance(distanceMatrixResponse);
        assertThat(distance).contains(100,50,150);
    }

    @Test
    public void givenShopsAndDistancesThenBuildShopDistanceMap() {
        Map<Shop,Location> shopLocationMap = new HashMap<>(1);
        Shop shop1 = getShop();
        Location location1 = getLocation();
        shopLocationMap.put(shop1,location1);
        Shop shop2 = new Shop();
        shop2.setShopName("Nokia");
        shopLocationMap.put(shop2,location1);
        List<Integer> distances = Arrays.asList(100,50);
        RetailRepository retailRepository = RetailRepository.getInstance();
        Map<Shop, Integer> shopIntegerMap = retailRepository.getShopDistanceMap(shopLocationMap,distances);
        assertThat(shopIntegerMap).containsKeys(shop1,shop2);
        assertThat(shopIntegerMap).containsValues(100,50);
    }

    @Test
    public void addShop() {
        Shop shop1 = this.getShop();
        RetailRepository retailRepository = RetailRepository.getInstance();
        ShopStatus shopStatus = retailRepository.buildShopStatus(shop1);
        assertThat(shopStatus.getMessage()).isEqualTo("Added the shop");
    }

    private Location getLocation() {
        Location location = new Location();
        location.setLat("51.53664141970851");
        location.setLng("0.05087131970849797");
        return location;
    }

    private CustomerLocation getCustomerLocation() {
        CustomerLocation myLocation = new CustomerLocation();
        myLocation.setLat("51.53664141970851");
        myLocation.setLng("0.05087131970849797");
        return myLocation;
    }

    private Shop getShop() {
        Shop shop = new Shop();
        shop.setShopName("Moonlight Eletric Company");
        shop.setAddressLine1("Star Lane, Leeds, LE 5XY");
        shop.setShopAddressNumber(3);
        shop.setCity("Leeds");
        shop.setShopAddressPostCode("LE 5XY");
        return shop;
    }
}
