package com.springJPA.airline.model;

import java.io.Serializable;
//import java.util.Arrays;
//import java.util.List;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
//import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.springJPA.airline.repo.PassengerRepository;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

	private static final long serialVersionUID = -8261334108709924377L;

	// private String orderno;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "orderno")
//	@JoinTable(name="reservation_list_flights", inverseJoinColumns={
//			@JoinColumn(table="reservation", name="reservation_orderno", referencedColumnName="orderno")
//	})
	private int orderno;

	// @Column(name = "pid")
	// private String pid;

	// @ElementCollection
	// private List<String> listFlights;

	@Column(name = "price")
	private int price;

	@Column(name = "flight_id")
	private int flight_id;
	
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "flight_pass_res", joinColumns = {
//			@JoinColumn(table = "reservation", name = "orderno", referencedColumnName = "orderno"),
//			@JoinColumn(table="passenger", name="pid", referencedColumnName="id"),
//			@JoinColumn(table="flight", name="number", referencedColumnName="number")})
	@JoinTable(name="reservation_flight", joinColumns = {
			@JoinColumn(name="orderno")
	}, inverseJoinColumns={
		@JoinColumn(name="number")	
	})
	private List<Flight> flights;

	@ManyToOne
	@JoinColumn(name = "pid")
	private Passenger passenger;

	// public Reservation(String pid, String flights) {
	// this.pid = pid;
	// this.listFlights = Arrays.asList(flights.split("\\[,\\]"));
	// this.price = getPrice(flights);
	// }

	public Reservation(Passenger passenger, List<Flight> flights) {
		this.passenger = passenger;
		this.flights = flights;
	}

	public Reservation() {
	}

	public int getPrice(String flights) {
		// TODO Auto-generated method stub
		return 240;
	}

	public int getOrderno() {
		return orderno;
	}

	public void setOrderno(int orderNumber) {
		this.orderno = orderNumber;
	}

	// public String getPid() {
	// return pid;
	// }
	//
	// public void setPid(String pid) {
	// this.pid = pid;
	// }
	//
	// public List<String> getlistFlights() {
	// return listFlights;
	// }
	//
	// public void setlistFlights(List<String> listFlights) {
	// this.listFlights = listFlights;
	// }

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Reservation ? this.orderno == ((Reservation) obj).getOrderno() : false;
	}
	
	@Override
	public int hashCode() {
		return String.valueOf(this.getOrderno()).hashCode();
	}

}

/*
 * CREATE TABLE reservation( orderno INT NOT NULL AUTO_INCREMENT, pid
 * VARCHAR(10), flights VARCHAR(20), price INT, PRIMARY KEY (orderno) );
 */