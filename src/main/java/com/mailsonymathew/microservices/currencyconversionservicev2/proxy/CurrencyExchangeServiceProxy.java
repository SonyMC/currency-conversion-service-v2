// Proxy Interface that allows Currency Conversion Service to talk to Currency Exchange Service  

package com.mailsonymathew.microservices.currencyconversionservicev2.proxy;

//import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mailsonymathew.microservices.currencyconversionservicev2.beans.CurrencyConversionBean;




//@FeignClient(name="currency-exchange-service-v2",url="localhost:8000")  // -> Without Eureka Naming Serve; If we use Feign, we do not need to specify port in Feign client .
@FeignClient(name="currency-exchange-service-v2")   // -> use with Eureka Naming Client and Feign will automatically use teh Load Balaciing available in Eureka naming Client. 
public interface CurrencyExchangeServiceProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);

}
