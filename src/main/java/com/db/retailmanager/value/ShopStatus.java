package com.db.retailmanager.value;

/**
 * Created by Rajesh on 15-May-17.
 */
public class ShopStatus {
    private String shopName;
    private int shopAddressNumber;
    private String addressLine1;
    private String city;
    private String message;
    private boolean success;

    public ShopStatus(Shop shop) {
        this.shopName = shop.getShopName();
        this.shopAddressNumber = shop.getShopAddressNumber();
        this.addressLine1 = shop.getAddressLine1();
        this.city = shop.getCity();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String responseMessage) {
        this.message = responseMessage;
    }

    public int getShopAddressNumber() {
        return shopAddressNumber;
    }

    public void setShopAddressNumber(int shopAddressNumber) {
        this.shopAddressNumber = shopAddressNumber;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
