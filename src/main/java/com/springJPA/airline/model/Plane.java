package com.springJPA.airline.model;

//import java.io.Serializable;

//import javax.persistence.Column;
import javax.persistence.Embeddable;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

@Embeddable
//@Table(name = "plane")
public class Plane{ // implements Serializable{
	private String model;
	private int capacity;
	private String manufacturer;
	private int yearOfManufacture;
	
	public Plane(int capacity, String model, String manufacturer, int  yearOfManufacture){
        this.capacity=capacity;
        this.model=model;
        this.manufacturer=manufacturer;
        this.yearOfManufacture=yearOfManufacture;
    }
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public int getYearOfManufacture() {
		return yearOfManufacture;
	}
	public void setYearOfManufacture(int yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}
	
	
//	private static final long serialVersionUID = 7730651636315478298L;
	
/*	@Column(name = "capacity")
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
	*/
}
