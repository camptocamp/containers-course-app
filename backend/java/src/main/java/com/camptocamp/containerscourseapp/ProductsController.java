package com.camptocamp.containerscourseapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ProductsController {

    
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final Map<String,Integer> views = new HashMap<String,Integer>(); //number of views
    private static final Map<String,Integer> buys = new HashMap<String,Integer>(); //number of buys
    // private static final Map<String, Product> allProducts = new HashMap<String,Product>(); //all Product objects, indexed by their name

//  * App requirements:

//  * http endpoints:
//    * '/product/<product>': describe one product and display stats (view and buy). call to this page increment view stats
//    * return json with addtional fileds: view and buy
   
//  * '/buy/<product>': buy a product, call to this page increment buy stats
//      * return 200 with html

    public ProductsController() {
        LOGGER.info("init map");
        // init from json
        views.put("geomapfish",0);
        views.put("georchestra",0);
        views.put("geonetwork",0);

        buys.put("geomapfish",0);
        buys.put("georchestra",0);
        buys.put("geonetwork",0);
    }

    @Autowired
    ResourceLoader resourceLoader;

    @RequestMapping("/")
	public String index() {
        JSONParser jsonParser = new JSONParser();
        Resource resource = resourceLoader.getResource("classpath:data.json");
        String data = null;
        try {
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            data = new String(bdata, StandardCharsets.UTF_8);
            LOGGER.info(data);
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        }
        return data;
    }

    @GetMapping("/products/{product}")
    // * '/product/<product>': describe one product and display stats (view and buy). call to this page increment view stats
    // * return json with addtional fileds: view and buy
    public ResponseEntity<String> read(@PathVariable("product") String product) {

        // Debug display of views/buys content 
        LOGGER.info(Arrays.toString((views.keySet().toArray(new String[0]))));
        LOGGER.info(Arrays.toString(views.values().toArray(new Integer[0])));
     
        if (!views.containsKey(product)) {
            LOGGER.error(product + " not found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        int productViews = views.get(product);
        views.put(product, productViews+1);

        StringBuilder sb = new StringBuilder();
        sb.append(product)
            .append(" viewed : ").append(Integer.toString(views.get(product)))
            .append(" bought : ").append(Integer.toString(buys.get(product)));
    
        return ResponseEntity.ok(sb.toString());
    }
}
