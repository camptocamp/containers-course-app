package com.camptocamp.containerscourseapp;

import org.json.simple.JSONObject;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class Product {

    public final static String KEY_NAME = "name";
    public final static String KEY_DESCRIPTION = "description";
    public final static String KEY_VIEW = "view";
    public final static String KEY_BUY = "buy";

    private String name;
    private String description;
    private int view;
    private int buy;
    private Counter viewCounter;
    private Counter buyCounter;

    public Product(String name, String description, MeterRegistry meterRegistry) {
        this.name = name;
        this.description = description;
        this.viewCounter = Counter.builder("product_view")
            .tags("product", name)
            .description("The number of products view")
            .register(meterRegistry);
        this.buyCounter = Counter.builder("product_buy")
            .tags("product", name)
            .description("The number of products buy")
            .register(meterRegistry);
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

    public void incrementView() {
        this.viewCounter.increment();
        this.view += 1;
    }

    public void incrementBuy() {
        this.buyCounter.increment();
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
