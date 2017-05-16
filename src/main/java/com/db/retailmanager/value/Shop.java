package com.db.retailmanager.value;

/**
 * Created by Rajesh on 15-May-17.
 */
public class Shop {
    private String shopName;
    private int shopAddressNumber;
    private String addressLine1;
    private String city;

    private String shopAddressPostCode;

    public int getShopAddressNumber() {
        return shopAddressNumber;
    }

    public void setShopAddressNumber(int shopAddressNumber) {
        this.shopAddressNumber = shopAddressNumber;
    }

    public String getShopAddressPostCode() {
        return shopAddressPostCode;
    }

    public void setShopAddressPostCode(String shopAddressPostCode) {
        this.shopAddressPostCode = shopAddressPostCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shop)) return false;
        Shop shop = (Shop) o;
        return getShopName().equals(shop.getShopName());
    }

    @Override
    public int hashCode() {
        return getShopName().hashCode();
    }
}
