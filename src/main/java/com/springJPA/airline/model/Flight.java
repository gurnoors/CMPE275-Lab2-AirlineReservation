package com.springJPA.airline.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flight")
public class Flight implements Serializable{

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
	
	@Embedded
	private Plane plane;
	
	public Flight(String number, int price, String fromSrc, String toDest, Date departureTime, Date arrivalTime, String description, 
								Plane plane) {
		this.number = number;
		this.price = price;
		this.fromSrc = fromSrc;
		this.toDest = toDest;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.description = description;
		this.plane = plane;
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
	
}
// will create the table automatically
