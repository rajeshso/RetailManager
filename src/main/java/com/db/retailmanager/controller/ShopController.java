package com.db.retailmanager.controller;

import com.db.retailmanager.model.RetailRepository;
import com.db.retailmanager.value.CustomerLocation;
import com.db.retailmanager.value.NearestShops;
import com.db.retailmanager.value.Shop;
import com.db.retailmanager.value.ShopStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ShopController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/addShop", method = RequestMethod.POST)
    public ResponseEntity<ShopStatus> addShop(@RequestBody Shop shop) {
        logger.debug("addShop called with " + shop);
        ShopStatus shopStatus = new ShopStatus(shop);
        RetailRepository retailRepository = RetailRepository.getInstance();
        try {
            shopStatus = retailRepository.addShop(shop);
            shopStatus.setSuccess(true);
        } catch (Exception e) {
            shopStatus.setSuccess(false);
            shopStatus.setMessage(e.getMessage());
        }
        return new ResponseEntity<ShopStatus>(shopStatus, HttpStatus.OK);
    }

    @RequestMapping(value = "/nearestShops", method = RequestMethod.POST)
    public ResponseEntity<NearestShops> nearestShops(@RequestBody CustomerLocation customerLocation) {
        logger.debug("nearestShops called with " + customerLocation);
        NearestShops nearestShops = new NearestShops();
        RetailRepository retailRepository = RetailRepository.getInstance();
        try {
            nearestShops = retailRepository.getNearestShops(customerLocation);
            nearestShops.setSuccess(true);
        } catch (Exception e) {
            nearestShops.setSuccess(false);
            nearestShops.setMessage(e.getMessage());
        }
        return new ResponseEntity<NearestShops>(nearestShops, HttpStatus.OK);
    }
}
