package com.camptocamp.containerscourseapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
public class ProductsController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String dataFileName = "data.json";
    private static final Map<String,Integer> views = new HashMap<String,Integer>(); //number of views
    private static final Map<String,Integer> buys = new HashMap<String,Integer>(); //number of buys

    @Autowired
    ResourceLoader resourceLoader;


    @RequestMapping("/")
	public String index() throws IOException, ParseException {
        return this.readProductList().toJSONString();
    }

    @GetMapping("/products/{product}")
    // * '/product/<product>': describe one product and display stats (view and buy). call to this page increment view stats
    // * return json with addtional fileds: view and buy
    public ResponseEntity<String> getProduct(@PathVariable("product") String product)
            throws IOException, ParseException {
        readProductList(); //read or re-read product list to make sure stats are initialized
     
        if (!views.containsKey(product)) {
            LOGGER.error(product + " not found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        int productViews = views.get(product);
        views.put(product, productViews+1);
    
        return ResponseEntity.ok(formatResponse(product));
    }


    @GetMapping("/buy/{product}")
    //  * '/buy/<product>': buy a product, call to this page increment buy stats
    //  * return 200 with html
    public ResponseEntity<String> buyProduct(@PathVariable("product") String product)
            throws IOException, ParseException {
        readProductList(); //read or re-read product list to make sure stats are initialized
     
        if (!buys.containsKey(product)) {
            LOGGER.error(product + " not found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        int productBought = buys.get(product);
        buys.put(product, productBought+1);
    
        return ResponseEntity.ok(formatResponse(product));
    }

    private String formatResponse(String product) {
        StringBuilder sb = new StringBuilder();
        sb.append(product)
            .append(" viewed : ").append(Integer.toString(views.get(product)))
            .append(" bought : ").append(Integer.toString(buys.get(product)));
        return sb.toString();
    }

    private JSONObject readProductList() throws IOException, ParseException {
        Resource resource = resourceLoader.getResource("classpath:" + dataFileName);
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

    private void parseProductList(JSONObject productList) 
    {
        //Get product object within list
        JSONArray productArray = (JSONArray) productList.get("products");

        //Iterate over employee array
        productArray.forEach( productObject -> parseProduct( (JSONObject) productObject ) );
    }

    private void parseProduct(JSONObject productObject) {
        String name = (String) productObject.get("name");    
        // String desc = (String) productObject.get("description");
        // Product product = new Product(name,desc);
        initStats(name);
    }
    
    private void initStats(String name) {
        if (!views.containsKey(name)) {
            LOGGER.info("Initialize viewing stats for " + name);
            views.put(name, 0);
        }
        if (!buys.containsKey(name)) {
            LOGGER.info("Initialize buying stats for " + name);
            buys.put(name, 0);
        }
    }

}
