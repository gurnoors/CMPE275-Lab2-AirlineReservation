package com.springJPA.airline.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.client.HttpServerErrorException;

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
	FlightRepository fightRepo;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {
		return "Welcome to Airline Services";
	}

	// Spec1 - Get a passenger using json
	@RequestMapping(value = "/passenger/{id}", method = { RequestMethod.GET })
	public ResponseEntity<Passenger> getPassenger(@PathVariable("id") String id,
			@RequestParam(value = "json", defaultValue = "false") Boolean json) {
		Passenger passenger = passRepo.findOne(Integer.parseInt(id));
		if (passenger == null) {
			return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
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
	public ResponseEntity<Passenger> updatePassenger(@PathVariable("idStr") String idStr,
			@RequestParam String firstname, @RequestParam String lastname, @RequestParam int age,
			@RequestParam String gender, @RequestParam String phone) {
		int id = Integer.parseInt(idStr);
		Passenger passenger = passRepo.findOne(id);
		if (passenger == null) {
			return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
		}

		passRepo.updatePassengerDetails(firstname, lastname, age, gender, phone, id);
		Passenger passengerUp = passRepo.findOne(id);

		return new ResponseEntity<Passenger>(passengerUp, HttpStatus.OK);
	}

	// Spec5 - Delete a passenger
	@RequestMapping(value = "/passenger/{id}", method = { RequestMethod.DELETE })
	public String deletePassenger(@PathVariable("id") String id) {
		Passenger passenger = passRepo.findOne(Integer.parseInt(id));
		if (passenger == null) {
			return "Passenger not present";
		}

		passRepo.delete(Integer.parseInt(id));

		return "Passenger deleted";
	}

	// Spec7 - Make a reservation
	@RequestMapping(value = "/reservation", method = { RequestMethod.POST })
	public ResponseEntity<Reservation> makeReservation(@RequestParam String passengerId,
			@RequestParam String flightLists) {
		Reservation reservation = new Reservation(passengerId, flightLists);

		resRepo.save(reservation);

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

		fightRepo.save(flight);
		return new ResponseEntity<Flight>(flight, HttpStatus.OK);
	}

	// -----------------------------------------------------------

	// Spec6 - Get a reservation back as JSON
	@RequestMapping(value = "/reservation/{id}", method = { RequestMethod.GET })
	public ResponseEntity<Reservation> getReservation(@PathVariable("id") String id) {
		Reservation res = resRepo.findOne(Integer.valueOf(id));
		if (res == null)
			return new ResponseEntity<Reservation>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<Reservation>(res, HttpStatus.OK);
	}

	// Spec8 - Update a reservation
	// TODO: check conditions like: flight exists, non-overlapping, etc
	@RequestMapping(value = "/reservation/{idStr}", method = { RequestMethod.POST })
	public ResponseEntity<?> updateReservation(@PathVariable("idStr") String idStr,
			@RequestParam(required = false) String flightsAdded,
			@RequestParam(required = false) String flightsRemoved) {
		int id = Integer.parseInt(idStr);
		Reservation reservation = resRepo.findOne(id);

		// remove flights
		if (flightsRemoved != null) {
			if (flightsRemoved.isEmpty()) {
				return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.BAD_REQUEST.value(),
						"flightsRemoved String empty. Remove the parameter."), HttpStatus.BAD_REQUEST);
			}
			Set<String> flightsInRes = new HashSet<String>(reservation.getlistFlights());
			for (String toRemove : flightsRemoved.split("\\[,\\]")) {
				boolean wasPresent = flightsInRes.remove(toRemove);
				if (!wasPresent) {
					// TODO: Error mesage

					return new ResponseEntity<Reservation>(HttpStatus.NOT_FOUND);
				}
			}
			reservation.setlistFlights(new ArrayList<String>(flightsInRes));
		}

		// add flights
		if (flightsAdded != null) {
			if (flightsAdded.isEmpty()) {
				return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.BAD_REQUEST.value(),
						"flightsRemoved String empty. Remove the parameter."), HttpStatus.BAD_REQUEST);
			}
			// check all flights exist
			List<String> flightsAddedList = Arrays.asList(flightsAdded.split("\\[,\\]"));
			for (String flightId : flightsAddedList) {
				if (!fightRepo.exists(flightId)) {
					return new ResponseEntity<ControllerError>(new ControllerError(HttpStatus.BAD_REQUEST.value(),
							"Flight " + flightId + " does not exist"), HttpStatus.BAD_REQUEST);
				}
			}

			// validate and add flights
			if (isValidated(reservation, flightsAddedList)) {
				List<String> updatedFlights = new ArrayList<>();
				updatedFlights.addAll(reservation.getlistFlights());
				updatedFlights.addAll(flightsAddedList);
				reservation.setlistFlights(updatedFlights);
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
	private boolean isValidated(Reservation reservation, List<String> flightsAdded) {
		List<Flight> existingFlights = new ArrayList<Flight>();
		// populate list
		if (reservation.getlistFlights() != null) {
			for (String flightID : reservation.getlistFlights()) {
				Flight toAdd = fightRepo.findOne(flightID);
				if (toAdd != null) {
					existingFlights.add(toAdd);
				}
			}
		}

		for (String flightID : flightsAdded) {
			Flight newFlight = fightRepo.findOne(flightID);
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
			// TODO
			// return new ResponseEntity<String>("Reservation with number XXX is
			// canceled successfully",
			// HttpStatus.OK);
			return new ResponseEntity<Reservation>(HttpStatus.OK);
		}
	}

	// spec 11 - Get a flight back as JSON
	@RequestMapping(value = "/flight/{id}", method = { RequestMethod.GET })
	public ResponseEntity<Flight> getFlight(@PathVariable("id") String id,
			@RequestParam(value = "json", defaultValue = "false") Boolean json) {
		Flight flight = fightRepo.findOne(id);
		if (flight == null) {
			return new ResponseEntity<Flight>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Flight>(flight, HttpStatus.OK);
	}

	// spec 14 - delete a flight
	@RequestMapping(value = "/airline/{id}", method = { RequestMethod.DELETE })
	public ResponseEntity<Flight> deleteFlight(@PathVariable("id") String id) {
		Flight flight = fightRepo.findOne(id);
		if (flight == null) {
			return new ResponseEntity<Flight>(HttpStatus.NOT_FOUND);
		} else {
			fightRepo.delete(id);
			// TODO
			// return new ResponseEntity<String>("Reservation with number XXX is
			// canceled successfully",
			// HttpStatus.OK);
			return new ResponseEntity<Flight>(HttpStatus.OK);
		}
	}

}