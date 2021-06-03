package com.camptocamp.containerscourseapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.MeterRegistry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//  * App requirements:

//  * http endpoints:
//    * '/product/<product>': describe one product and display stats (view and buy). call to this page increment view stats
//    * return json with addtional fileds: view and buy
   
//  * '/buy/<product>': buy a product, call to this page increment buy stats
//      * return 200 with html

// {"name": "geomapfish", "description": "GeoMapFish allows to build rich and extensible WebGIS in an easy and flexible way. 
// It has been developped to fulfill the needs of various actors in the geospatial environment, might it be public, private or academic actors.",
// "view": 1, "buy": 0}
@RestController
public class ProductsController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String DATA_FILENAME = "data.json";

    @Autowired
    MeterRegistry meterRegistry;

    private static final Map<String,Product> products =  new HashMap<String,Product>();

    @Autowired
    ResourceLoader resourceLoader;

    @RequestMapping("/")
	public String index() throws IOException, ParseException {
        return this.readProductList().toJSONString();
    }

    @GetMapping("/product/{product}")
    // * '/product/<product>': describe one product and display stats (view and buy). call to this page increment view stats
    // * return json with addtional fileds: view and buy
    public ResponseEntity<String> getProduct(@PathVariable("product") String productName)
            throws IOException, ParseException {
        readProductList(); //read or re-read product list to make sure stats are initialized
     
        if (!products.containsKey(productName)) {
            LOGGER.error(productName + " not found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        products.get(productName).incrementView();
    
        return ResponseEntity.ok(formatResponse(productName));
    }


    @GetMapping("/buy/{product}")
    //  * '/buy/<product>': buy a product, call to this page increment buy stats
    //  * return 200 with html
    public ResponseEntity<String> buyProduct(@PathVariable("product") String productName)
            throws IOException, ParseException {
        readProductList(); //read or re-read product list to make sure stats are initialized
     
        if (!products.containsKey(productName)) {
            LOGGER.error(productName + " not found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        products.get(productName).incrementBuy();

        return ResponseEntity.ok(formatResponse(productName));
    }

    private String formatResponse(String productName) {
        return products.get(productName).toString();
    }

    private JSONObject readProductList() throws IOException, ParseException {
        Resource resource = null;
        resource = resourceLoader.getResource("classpath:" + DATA_FILENAME); // Useful to load test resources
        if (!resource.exists()) {
            LOGGER.error(DATA_FILENAME + " not found in the classpath");
        }
        LOGGER.debug("Reading products from " + resource.getURI());
        
		return readDataJson(resource);
    }

    private JSONObject readDataJson(Resource resource) throws IOException, ParseException {
        JSONObject productList = null;
        JSONParser jsonParser = new JSONParser();
        try (Reader reader = new InputStreamReader(resource.getInputStream()))
        {
            Object obj = jsonParser.parse(reader);
 
            productList = (JSONObject) obj;
            parseProductList(productList); 
        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException",e);
            throw e;
        } catch (IOException e) {
            LOGGER.error("IOException",e);
            throw e;
        } catch (ParseException e) {
            LOGGER.error("ParseException",e);
            throw e;
        }
        return productList;
    }

    private void parseProductList(JSONObject productList) {
        JSONArray productArray = (JSONArray) productList.get("products");
        productArray.forEach( productObject -> parseProduct( (JSONObject) productObject ) );
    }

    private void parseProduct(JSONObject productObject) {
        String name = (String) productObject.get("name");
        if (!products.containsKey(name)) {
            LOGGER.debug("Parsing product named '" + name + "'");
            String description = (String) productObject.get("description");
            Product product = new Product(name, description, this.meterRegistry);
            products.put(name, product);
        } else {
            LOGGER.debug("Product named '" + name + "' already parsed... skipping");
        }
    }
}
