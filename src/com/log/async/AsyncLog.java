package com.log.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

public class AsyncLog {

	// It should be replace by the service which is going to store the
	// information in the data base, for example CustomerTrafficService
	
	// @Autowired
	// private CustomerTrafficService customerTrafficService;
	//
	// @Async
	// public void persistLog(CustomerTraffic customerTraffic) {
	// customerTrafficService.addCustomerTraffic(customerTraffic);
	// }
}
