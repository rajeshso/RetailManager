package com.db.retailmanager.value;

/**
 * Created by Rajesh on 16-May-17.
 */
public class NearestShops {
    private Shop[] shops;
    private String message;
    private boolean success;


    public Shop[] getShops() {
        return shops;
    }

    public void setShops(Shop[] shops) {
        this.shops = shops;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean b) {
        this.success = b;
    }

    public boolean isSuccess() {
        return success;
    }
}
