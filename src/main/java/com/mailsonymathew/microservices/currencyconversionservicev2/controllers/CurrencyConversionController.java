package com.mailsonymathew.microservices.currencyconversionservicev2.controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.mailsonymathew.microservices.currencyconversionservicev2.beans.CurrencyConversionBean;
import com.mailsonymathew.microservices.currencyconversionservicev2.proxy.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	@Value("${currencyexchange.uri}")
	private String currencyExchangeUri; // will get this from the application.properties 

	
	// Example of old method using rest template for calling currency exchange microservice 
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){
		
		
		
		// the following hash map is used to provide values for placeholder elements ( i.e. form,to) in the uri 
		Map<String,String> uriVariable = new HashMap<>();
		uriVariable.put("from", from);  // get value of "from' from request uri
		uriVariable.put("to", to);  // get value of "to' from request uri
		
	//	ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,uriVariable);
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(currencyExchangeUri, CurrencyConversionBean.class,uriVariable);
		
		CurrencyConversionBean response = responseEntity.getBody();
		
		logger.info("{}",response);
		
		return new CurrencyConversionBean(response.getId(),
				                          from, to, response.getConversionMultiple(), 
				                          quantity, quantity.multiply(response.getConversionMultiple()),
				                          response.getPort() + " " + "Rest Template");

	}
	
	

	
	// Example of new method using feign for calling currency exchange microservice 
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){
		
		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);
		
		logger.info("{}",response);
		
		return new CurrencyConversionBean(response.getId(),
				                          from, to, response.getConversionMultiple(),
				                          quantity, quantity.multiply(response.getConversionMultiple()),
				                          response.getPort()+ " " + " Feign Proxy");
		

	}
	
	
//	@GetMapping("/currency-conversion/currency-conversion-apidoc/")
//	public URI getApiDoc()  {
//	   String docUrl="/swagger-ui.html"; 
//       // Build doc location uri
//	   URI docLocation = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(docUrl).build().toUri();
//       return docLocation;
//
//
//	}
//	
//	@GetMapping("/currency-conversion/currency-conversion-apidef/")
//	public URI getApiDef()  {
//	   String docUrl="/v3/api-docs"; 
//       // Build defintion location uri
//	   URI defLocation = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(docUrl).build().toUri();
//       return defLocation;
//
//
//	}
	



}
