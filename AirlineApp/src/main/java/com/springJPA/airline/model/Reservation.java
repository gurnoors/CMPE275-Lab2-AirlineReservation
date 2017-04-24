package com.springJPA.airline.model;

import java.io.Serializable;
//import java.util.Arrays;
//import java.util.List;

import javax.persistence.Column;
//import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable{

	private static final long serialVersionUID = -8261334108709924377L;
	//private List<String> listFlights;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "orderno")
	private String orderno;
	
	@Column(name = "pid")
	private String pid;
	
	@Column(name = "flights")
	private String flights;
	
	@Column(name = "price")
	private int price;
	
	public Reservation(String pid, String flights) {
		this.pid = pid;
		this.flights = flights; //Arrays.asList(lFlights.split("\\s*,\\s*"));
		//this.listFlights = Arrays.asList(lFlights.split("\\s*,\\s*"));
		this.price = getPrice(flights);
	}

	private int getPrice(String flights) {
		// TODO Auto-generated method stub
		return 240;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderNumber) {
		this.orderno = orderNumber;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	/*
	public List<String> getlistFlights() {
		return listFlights;
	}

	public void setlistFlights(List<String> listFlights) {
		this.listFlights = listFlights;
	}
	*/
	public String getFlights() {
		return flights;
	}

	public void setFlights(String flights) {
		this.flights = flights;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}

/*
CREATE TABLE reservation(
   orderno INT NOT NULL AUTO_INCREMENT, 
   pid VARCHAR(10),
   flights VARCHAR(20),
   price INT,
   PRIMARY KEY (orderno)
);
*/