package com.camptocamp.containerscourseapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ProductsController {

    final Logger LOGGER = LoggerFactory.getLogger(getClass());
     
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

}
