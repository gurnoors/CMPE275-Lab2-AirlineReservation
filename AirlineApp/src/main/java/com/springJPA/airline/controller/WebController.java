package com.springJPA.airline.controller;

import java.util.Date;

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

import com.springJPA.airline.model.Flight;
import com.springJPA.airline.model.Passenger;
import com.springJPA.airline.model.Plane;
import com.springJPA.airline.model.Reservation;
import com.springJPA.airline.repo.FlightRepository;
import com.springJPA.airline.repo.PassengerRepository;
import com.springJPA.airline.repo.ReservationRepository;


@RestController
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class WebController {
	@Autowired
	PassengerRepository repository;
	
	@Autowired
	ReservationRepository repository1;
	
	@Autowired
	FlightRepository repository2;
	
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
	@RequestMapping(value = "/passenger", method = {RequestMethod.POST})
	public ResponseEntity<Passenger> createPassenger(String firstname, String lastname, int age, String gender, String phone){	
		Passenger passenger = new Passenger(firstname, lastname, age, gender, phone);
		repository.save(passenger);
		
        return new ResponseEntity<Passenger>(passenger, HttpStatus.OK);
	}
	
// Spec4 - Update a passenger
	@RequestMapping(value = "/passenger/{id}", method = {RequestMethod.PUT})
	public ResponseEntity<Passenger> updatePassenger(@PathVariable("id") String id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam int age, @RequestParam String gender, @RequestParam String phone){
		Passenger passenger = repository.findOne(id);
		if (passenger == null) {
            return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
        }
		
		repository.updatePassengerDetails(firstname, lastname, age, gender, phone, id);
		Passenger passengerUp = repository.findOne(id);
		
		return new ResponseEntity<Passenger>(passengerUp, HttpStatus.OK);
	}
	
// Spec5 - Delete a passenger
	@RequestMapping(value = "/passenger/{id}", method = {RequestMethod.DELETE})
	public String deletePassenger(@PathVariable("id") String id){
		Passenger passenger = repository.findOne(id);
		if (passenger == null) {
            return "Passenger not present";
        }
		
		repository.delete(id);
		
		return "Passenger deleted";
	}
	
// Spec7 - Make a reservation
	@RequestMapping(value = "/reservation", method = {RequestMethod.POST})
	public ResponseEntity<Reservation> makeReservation(@RequestParam String pId, @RequestParam String fLists){
		Reservation reservation = new Reservation(pId, fLists);
		
		repository1.save(reservation);
		
		return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
	}
	
// Spec13 - Create or update a flight
	@RequestMapping(value = "/flight/{id}", method = {RequestMethod.POST})
	public ResponseEntity<Flight> addFlight(@PathVariable("id") String id, @RequestParam int price, @RequestParam String from, @RequestParam String to, @RequestParam Date departureTime, @RequestParam Date arrivalTime, @RequestParam String description, @RequestParam int capacity, @RequestParam String model, @RequestParam String manufacturer, @RequestParam int yearOfManufacturer){
		Flight flight = new Flight(id, price, from, to, departureTime, arrivalTime, description, new Plane(capacity, model, manufacturer, yearOfManufacturer));
		
		repository2.save(flight);
		return new ResponseEntity<Flight>(flight, HttpStatus.OK);
	}
}