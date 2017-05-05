package com.springJPA.airline.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
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
	PassengerRepository passRepo;

	@Autowired
	ReservationRepository resRepo;

	@Autowired
	FlightRepository flightRepo;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {
		return "Welcome to Airline Services";
	}

	// Spec1 - Get a passenger using json
	@RequestMapping(value = "/passenger/{id}", method = { RequestMethod.GET })
	public ResponseEntity<?> getPassenger(@PathVariable("id") String id,
			@RequestParam(value = "json", defaultValue = "false") Boolean json) {
		Passenger passenger = passRepo.findOne(Integer.parseInt(id));
		if (passenger == null) {
			return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.NOT_FOUND.value(),
					"Not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Passenger>(passenger, HttpStatus.OK);
	}

	// Spec3 - Create a passenger
	@RequestMapping(value = "/passenger", method = { RequestMethod.POST })
	public ResponseEntity<Passenger> createPassenger(String firstname, String lastname, int age, String gender,
			String phone) {
		Passenger passenger = new Passenger(firstname, lastname, age, gender, phone);
		passRepo.save(passenger);

		return new ResponseEntity<Passenger>(passenger, HttpStatus.OK);
	}

	// Spec4 - Update a passenger
	@RequestMapping(value = "/passenger/{idStr}", method = { RequestMethod.PUT })
	public ResponseEntity<?> updatePassenger(@PathVariable("idStr") String idStr,
			@RequestParam String firstname, @RequestParam String lastname, @RequestParam int age,
			@RequestParam String gender, @RequestParam String phone) {
		int id = Integer.parseInt(idStr);
		Passenger passenger = passRepo.findOne(id);
		if (passenger == null) {
			return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.NOT_FOUND.value(),
					"Not found"), HttpStatus.NOT_FOUND);
		}

		passRepo.updatePassengerDetails(firstname, lastname, age, gender, phone, id);
		Passenger passengerUp = passRepo.findOne(id);

		return new ResponseEntity<Passenger>(passengerUp, HttpStatus.OK);
	}

	// Spec5 - Delete a passenger
	@RequestMapping(value = "/passenger/{id}", method = { RequestMethod.DELETE })
	public ResponseEntity<?> deletePassenger(@PathVariable("id") String id) {
		Passenger passenger = passRepo.findOne(Integer.parseInt(id));
		if (passenger == null) {
			return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.NOT_FOUND.value(),
					"Not found"), HttpStatus.NOT_FOUND);
		}

		passRepo.delete(Integer.parseInt(id));

		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	// Spec7 - Make a reservation
	@RequestMapping(value = "/reservation", method = { RequestMethod.POST })
	public ResponseEntity<?> makeReservation(@RequestParam String passengerId,
			@RequestParam String flightLists) {
		if(passengerId == null || passengerId.isEmpty()){
			return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.BAD_REQUEST.value(),
					"Bad Request. Passender ID not provided in request"), HttpStatus.BAD_REQUEST);
		}
		if(flightLists == null || flightLists.isEmpty()){
			return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.BAD_REQUEST.value(),
					"Bad Request. Flight ID(s) not provided in request"), HttpStatus.BAD_REQUEST);
		}
		Passenger passenger = passRepo.findOne(Integer.parseInt(passengerId));
		if(passenger == null){
			return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.BAD_REQUEST.value(),
					"Passenger with id " +passengerId+ " does not exist"), HttpStatus.BAD_REQUEST);
		}
		List<Flight> flights = new ArrayList<>();
		for(String flightId : flightLists.split("\\,")){
			Flight flight = flightRepo.findOne(flightId);
			if(flight == null){
				return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.BAD_REQUEST.value(),
						"Flight with id "+ flightId+ " does not exist"), HttpStatus.BAD_REQUEST);
			}
			flights.add(flight);
		}
		
		Reservation reservation = new Reservation(passenger, new ArrayList<Flight>());
		if(isValidated(reservation, flights)){
			reservation.setFlights(flights);
			resRepo.save(reservation);
		}else{
			return new ResponseEntity<String>("Flights conflicting", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
	}

	// Spec13 - Create or update a flight
	@RequestMapping(value = "/flight/{id}", method = { RequestMethod.POST })
	public ResponseEntity<Flight> addFlight(@PathVariable("id") String id, @RequestParam int price,
			@RequestParam String from, @RequestParam String to, @RequestParam Date departureTime,
			@RequestParam Date arrivalTime, @RequestParam String description, @RequestParam int capacity,
			@RequestParam String model, @RequestParam String manufacturer, @RequestParam int yearOfManufacture) {
		Flight flight = new Flight(id, price, from, to, departureTime, arrivalTime, description,
				new Plane(capacity, model, manufacturer, yearOfManufacture));

		flightRepo.save(flight);
		return new ResponseEntity<Flight>(flight, HttpStatus.OK);
	}

	// -----------------------------------------------------------

	// Spec6 - Get a reservation back as JSON
	@RequestMapping(value = "/reservation/{id}", method = { RequestMethod.GET })
	public ResponseEntity<?> getReservation(@PathVariable("id") String id) {
		Reservation res = resRepo.findOne(Integer.valueOf(id));
		if (res == null){
			return new ResponseEntity<Reservation>(HttpStatus.NOT_FOUND);
		}else{
			ResponseEntity<String> response = new ResponseEntity<String>(jsonifyReservation(res), HttpStatus.OK);
//			response.getHeaders().add("Content-Type", "application/json");
			return response;
		}
	}

	private String jsonifyReservation(Reservation res) {
		
		/*
		 * "reservation": {
		"orderNumber": "123",
		"price": "240",
		"passenger": {
			"id": " 123 ",
			"firstname": " John ",
			"lastname": " Oliver ",
			"age": " 21 ",
			"gender": " male ",
			"phone": " 4445556666 "
		},
		"flights": {

		 * 
		 */
		
		JSONObject reservationJson = new JSONObject();
		reservationJson.put("orderNumber", res.getOrderno());
		reservationJson.put("price", res.getPrice());
		
		JSONObject passengerJson = new JSONObject();
		Passenger passenger = res.getPassenger();
		passengerJson.put("id", String.valueOf(passenger.getId()));
		passengerJson.put("firstName", passenger.getFirstName());
		passengerJson.put("lastName", passenger.getLastName());
		passengerJson.put("age", String.valueOf(passenger.getAge()));
		passengerJson.put("gender", passenger.getGender());
		passengerJson.put("phone", String.valueOf(passenger.getPhone()));
		reservationJson.put("passenger", passengerJson);
		
		List<JSONObject> flightJsonList = new ArrayList<>();
		for(Flight flight : res.getFlights()){
			JSONObject flightJson = jsonifyFlight(flight);
			flightJson.remove("passengers");
			flightJsonList.add(flightJson);
		}
		
		reservationJson.put("flights", flightJsonList);
		
		return reservationJson.toString();
	}

	// Spec8 - Update a reservation
	// TODO: check conditions like: flight exists, non-overlapping, etc
	@RequestMapping(value = "/reservation/{idStr}", method = { RequestMethod.POST })
	public ResponseEntity<?> updateReservation(@PathVariable("idStr") String idStr,
			@RequestParam(required = false) String flightsAdded,
			@RequestParam(required = false) String flightsRemoved) {
		int id = Integer.parseInt(idStr);
		Reservation reservation = resRepo.findOne(id);
		
		if(reservation == null){
			//TODO error message
			return new ResponseEntity<Reservation>(HttpStatus.NOT_FOUND);
		}

		// remove flights
		if (flightsRemoved != null) {
			if (flightsRemoved.isEmpty()) {
				return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.BAD_REQUEST.value(),
						"flightsRemoved String empty. Remove the parameter."), HttpStatus.BAD_REQUEST);
			}
			Set<Flight> flightsInRes = new HashSet<Flight>(reservation.getFlights());
			for (String toRemove : flightsRemoved.split("\\[,\\]")) {
				boolean wasPresent = flightsInRes.remove(toRemove);
				if (!wasPresent) {
					return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.NOT_FOUND.value(),
							"flight id " + toRemove + " not found"), HttpStatus.NOT_FOUND);
				}
			}
			reservation.setFlights(new ArrayList<Flight>(flightsInRes));
		}

		// add flights
		if (flightsAdded != null) {
			if (flightsAdded.isEmpty()) {
				return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.BAD_REQUEST.value(),
						"flightsRemoved String empty. Remove the parameter."), HttpStatus.BAD_REQUEST);
			}
			// check all flights exist
			List<String> flightsAddedListStr = new ArrayList<>( Arrays.asList(flightsAdded.split("\\[,\\]")));
			List<Flight> flightsAddedList = new ArrayList<>();
			for (String flightId : flightsAddedListStr) {
				if (!flightRepo.exists(flightId)) {
					return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.BAD_REQUEST.value(),
							"Flight " + flightId + " does not exist"), HttpStatus.BAD_REQUEST);
				}else{
					flightsAddedList.add(flightRepo.findOne(flightId));
				}
			}

			// validate and add flights
			if (isValidated(reservation, flightsAddedList)) {
				List<Flight> updatedFlights = new ArrayList<>();
				updatedFlights.addAll(reservation.getFlights());
				updatedFlights.addAll(flightsAddedList);
				reservation.setFlights(updatedFlights);
			} else {
				return new ResponseEntity<ControllerError>(
						new ControllerError(HttpStatus.BAD_REQUEST.value(), "Flights are overlapping"),
						HttpStatus.BAD_REQUEST);
			}
		}

		resRepo.save(reservation);

		return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
	}

	/**
	 * 
	 * @param reservation
	 * @param flightsAdded
	 * @return true if flights are not overlapping (and fights exist)
	 */
	private boolean isValidated(Reservation reservation, List<Flight> flightsAdded) {
		List<Flight> existingFlights = reservation.getFlights();
		if(existingFlights == null)
			existingFlights = new ArrayList<Flight>();

		for (Flight newFlight : flightsAdded) {
			for (Flight oldFlight : existingFlights) {
				if (isOverlapping(oldFlight.getDepartureTime(), oldFlight.getArrivalTime(),
						newFlight.getDepartureTime(), newFlight.getArrivalTime())) {
					return false;
				}
			}
			// none of the existing flights overlap
			existingFlights.add(newFlight);
		} // do for all new flights
		return true;
	}

	public static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
		return !start1.after(end2) && !start2.after(end1);
	}

	// Spec 10 - Cancel a reservation
	@RequestMapping(value = "/reservation/{id}", method = { RequestMethod.DELETE })
	public ResponseEntity<?> deleteReservation(@PathVariable("id") String id) {
		Reservation res = resRepo.findOne(Integer.valueOf(id));
		if (res == null) {
			return new ResponseEntity<Reservation>(HttpStatus.NOT_FOUND);
		} else {
			resRepo.delete(Integer.valueOf(id));
			return new ResponseEntity<Reservation>(HttpStatus.OK);
		}
	}

	// spec 11 - Get a flight back as JSON
	@RequestMapping(value = "/flight/{id}", method = { RequestMethod.GET })
	public ResponseEntity<?> getFlight(@PathVariable("id") String id,
			@RequestParam(value = "json", defaultValue = "false") Boolean json) {
		Flight flight = flightRepo.findOne(id);
		if (flight == null) {
			return new ResponseEntity<Flight>(HttpStatus.NOT_FOUND);
		}
		
		//TODO: refactor: hacks 
//		List<Reservation> reservations = flight.getReservations();
//		List<Passenger> passengers = new ArrayList<>();
//		for(Reservation reservation : reservations){
//			passengers.add(reservation.getPassenger());
//		}
//		flight.setPassengers(passengers);

		return new ResponseEntity<String>(jsonifyFlight(flight).toString(), HttpStatus.OK);
	}

	private JSONObject jsonifyFlight(Flight flight) {
		
		/*
		 * 
		 * "number": "7",
  "price": 110,
  "fromSrc": "AA",
  "toDest": "BB",
  "departureTime": 1497036600000,
  "arrivalTime": 1497033000000,
  "seatsLeft": 0,
  "description": "EE",
  "plane": {
    "model": "HH",
    "capacity": 10,
    "manufacturer": "II",
    "yearOfManufacture": 1997
  },
  "passengers": [
    {
      "id": 1,
      "firstName": "Luna",
      "lastName": "Tic",
      "age": 11,
      "gender": "female",
      "phone": "12",
		 * 
		 */
		JSONObject json = new JSONObject();
		json.put("number", flight.getNumber());
		json.put("price", flight.getPrice());
		json.put("fromSrc", flight.getFromSrc());
		json.put("toDest", flight.getToDest());
		json.put("departureTime", flight.getDepartureTime().toString());//TODO
		json.put("arrivalTime", flight.getArrivalTime());
		json.put("seatsLeft", flight.getSeatsLeft());
		json.put("description", flight.getDescription());
		json.accumulate("plane", new JSONObject(flight.getPlane()));
		
		List<Reservation> reservations = flight.getReservations();
		List<Passenger> passengers = new ArrayList<>();
		for(Reservation reservation : reservations){
			passengers.add(reservation.getPassenger());
		}
//		List<Passenger> passengers = flight.getPassengers();
//		for(Passenger passenger : passengers){
//			passenger.setReservations(null);
//		}
		List<JSONObject> passengerJsonList = new  ArrayList<>();
		for(Passenger passenger: passengers){
			JSONObject passengerJson = new JSONObject();
			passengerJson.put("id", passenger.getId());
			passengerJson.put("age", passenger.getAge());
			passengerJson.put("firstName", passenger.getFirstName());
			passengerJson.put("lastName", passenger.getLastName());
			passengerJson.put("gender", passenger.getGender());
			passengerJson.put("phone", passenger.getPhone());
			passengerJsonList.add(passengerJson);
		}
		
		json.put("passengers", passengerJsonList);
		
		return json;
	}

	// spec 14 - delete a flight
	@RequestMapping(value = "/airline/{id}", method = { RequestMethod.DELETE })
	public ResponseEntity<Flight> deleteFlight(@PathVariable("id") String id) {
		Flight flight = flightRepo.findOne(id);
		if (flight == null) {
			return new ResponseEntity<Flight>(HttpStatus.NOT_FOUND);
		} else {
			flightRepo.delete(id);
			// TODO
			// return new ResponseEntity<String>("Reservation with number XXX is
			// canceled successfully",
			// HttpStatus.OK);
			return new ResponseEntity<Flight>(HttpStatus.OK);
		}
	}
	
	

}


