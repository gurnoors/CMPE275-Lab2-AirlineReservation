package com.springJPA.airline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springJPA.airline.model.Passenger;
import com.springJPA.airline.repo.AirlineRepository;


@RestController
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class WebController {
	@Autowired
	AirlineRepository repository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(){
		return "Welcome to Airline Services";
	}
	
// Spec1 - Get a passenger using json
	@RequestMapping(value = "/passenger/{id}", method = {RequestMethod.GET})
	public ResponseEntity<Passenger> getPassenger(@PathVariable("id") String id, @RequestParam(value="json", defaultValue="false") Boolean json){
		Passenger passenger = repository.findOne(id);
        if (passenger == null) {
            return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Passenger>(passenger, HttpStatus.OK);
	}
	
// Spec3 - Create a passenger
	@RequestMapping(value = "/passenger")
	public ResponseEntity<Passenger> createPassenger(String firstname, String lastname, int age, String gender, String phone){	
		Passenger passenger = new Passenger(firstname, lastname, age, gender, phone);
		repository.save(passenger);
		
        return new ResponseEntity<Passenger>(passenger, HttpStatus.OK);
	}
	
// Spec4 - Update a passenger
	@RequestMapping(value = "/passenger/{id}", method = {RequestMethod.PUT})
	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<Passenger> updatePassenger(@PathVariable("id") String id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam int age, @RequestParam String gender, @RequestParam String phone){
		
		Passenger passenger = repository.findOne(id);
		if (passenger == null) {
            return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
        }
		
		repository.updatePassengerDetails(firstname, lastname, age, gender, phone, id);
		Passenger passengerUp = repository.findOne(id);
		
		return new ResponseEntity<Passenger>(passengerUp, HttpStatus.OK);
	}
}