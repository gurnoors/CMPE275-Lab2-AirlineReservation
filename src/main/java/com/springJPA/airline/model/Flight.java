package com.springJPA.airline.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "flight")
public class Flight implements Serializable {

	private static final long serialVersionUID = -8208883729985104877L;

	@Id
	@Column(name = "number")
	private String number;

	@Column(name = "price")
	private int price;

	@Column(name = "fromSrc")
	private String fromSrc;

	@Column(name = "toDest")
	private String toDest;

	@Column(name = "departureTime")
	private Date departureTime;

	@Column(name = "arrivalTime")
	private Date arrivalTime;

	@Column(name = "seatsLeft")
	private int seatsLeft;

	@Column(name = "description")
	private String description;

//	public ResponseEntity<Flight> addFlight(@PathVariable("id") String id, @RequestParam int price,
//			@RequestParam String from, @RequestParam String to, @RequestParam("departureTime") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")Date departureTime,
//			@RequestParam("arrivalTime") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") Date arrivalTime, @RequestParam String description, @RequestParam int capacity,
//			@RequestParam String model, @RequestParam String manufacturer, @RequestParam int yearOfManufacture)

	@Embedded
	private Plane plane;
	
	@JsonIgnore
	@ManyToMany(mappedBy="flights")
	private List<Reservation> reservations;
	
//	@Transient
//	@JsonManagedReference
//	private List<Passenger> passengers;
	// @OneToMany
	// @JoinColumn(na)

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "passenger", joinColumns = { 
//			
//	}
////			@JoinColumn(name = "number") }
//	, inverseJoinColumns = {
//			// wrong -> passenger table has no info on Flight
//			// its actually a manyTpMany with Reservation table as the join
//			// table
//			@JoinColumn(name = "") }
//	)
//	private List<Passenger> passengers;

	public Flight(String number, int price, String fromSrc, String toDest, Date departureTime, Date arrivalTime,
			String description, Plane plane) {
		this.number = number;
		this.price = price;
		this.fromSrc = fromSrc;
		this.toDest = toDest;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.description = description;
		this.plane = plane;
	}

	public Flight() {
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getFromSrc() {
		return fromSrc;
	}

	public void setFromSrc(String fromSrc) {
		this.fromSrc = fromSrc;
	}

	public String getToDest() {
		return toDest;
	}

	public void setToDest(String toDest) {
		this.toDest = toDest;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getSeatsLeft() {
		return seatsLeft;
	}

	public void setSeatsLeft(int seatsLeft) {
		this.seatsLeft = seatsLeft;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Flight){
			Flight flight2 = (Flight) obj;
			return this.getNumber().equals(flight2.getNumber());
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.number.hashCode();
	}

//	public List<Passenger> getPassengers() {
//		return passengers;
//	}
//
//	public void setPassengers(List<Passenger> passengers) {
//		this.passengers = passengers;
//	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	

}
// will create the table automatically
