package com.springJPA.airline.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plane")
public class Plane implements Serializable{

	private static final long serialVersionUID = 7730651636315478298L;
	
	@Column(name = "capacity")
	private int capacity;
	
	@Id
	@Column(name = "model")
	private String model;

	@Column(name = "manufacturer")
	private String manufacturer;
	
	@Column(name = "yearOfManufacture")
	private int yearOfManufacture;
	
	protected Plane() {
	}

	public Plane(int capacity, String model, String manufacturer, int yearOfManufacture) {
		this.capacity = capacity;
		this.model = model;
		this.manufacturer = manufacturer;
		this.yearOfManufacture = yearOfManufacture;
	}
}
