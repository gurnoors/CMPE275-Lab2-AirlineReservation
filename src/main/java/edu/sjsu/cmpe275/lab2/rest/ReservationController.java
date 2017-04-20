package edu.sjsu.cmpe275.lab2.rest;

import edu.sjsu.cmpe275.lab2.entities.Passenger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
	
	@RequestMapping("/passenger")
	public Passenger createPassenger(){
		Passenger passenger = new Passenger();
		
		 
		
		return passenger;
	}

}
