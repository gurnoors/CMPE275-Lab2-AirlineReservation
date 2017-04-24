package com.springJPA.airline.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
//import javax.persistence.Embedded;
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
/*	
	@Embedded
	private Plane plane;
*/	
	public Flight(String number, int price, String fromSrc, String toDest, Date departureTime, Date arrivalTime, String description) {
		this.number = number;
		this.price = price;
		this.fromSrc = fromSrc;
		this.toDest = toDest;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.description = description;
	}
	
}
