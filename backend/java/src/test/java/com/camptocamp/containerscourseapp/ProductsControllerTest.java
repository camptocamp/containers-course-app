package com.camptocamp.containerscourseapp;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getProducts() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("products")));
	}

	@Test
	public void getProductGeomapfish() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/products/geomapfish").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("geomapfish viewed : 1 bought : 0")));
		mvc.perform(MockMvcRequestBuilders.get("/products/geomapfish").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("geomapfish viewed : 2 bought : 0")));
		mvc.perform(MockMvcRequestBuilders.get("/products/geomapfish").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("geomapfish viewed : 3 bought : 0")));
	}


	@Test
	public void buyProductGeorchestra() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/buy/georchestra").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("georchestra viewed : 0 bought : 1")));
		mvc.perform(MockMvcRequestBuilders.get("/buy/georchestra").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("georchestra viewed : 0 bought : 2")));
		mvc.perform(MockMvcRequestBuilders.get("/buy/georchestra").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("georchestra viewed : 0 bought : 3")));
	}
}
