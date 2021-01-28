package com.camptocamp.containerscourseapp;

import org.json.simple.JSONObject;

public class Product {

    public final static String KEY_NAME = "name";
    public final static String KEY_DESCRIPTION = "description";
    public final static String KEY_VIEW = "view";
    public final static String KEY_BUY = "buy";

    private String name;
    private String description;
    private int view;
    private int buy;

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
        this.view = 0;
        this.buy = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public void addView() {
        this.view += 1;
    }

    public int getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public void addBuy() {
        this.buy += 1;
    }

    public JSONObject toJson() {
        JSONObject js = new JSONObject();
        js.put(Product.KEY_NAME, this.name);
        js.put(Product.KEY_DESCRIPTION, this.description);
        js.put(Product.KEY_VIEW, this.view);
        js.put(Product.KEY_BUY, this.buy);
        return js;
    }

    public String toString() {
        return toJson().toString();
    }
}
