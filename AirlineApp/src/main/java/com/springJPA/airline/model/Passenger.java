package com.springJPA.airline.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//@javax.persistence.Entity(name = "passenger")
@Entity
@Table(name = "passenger", uniqueConstraints = {@UniqueConstraint(columnNames={"phone"})})
public class Passenger implements Serializable {

	private static final long serialVersionUID = -7954044712529235759L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "age")
	private int age;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "phone")
	private String phone;
	
	protected Passenger() {
	}

	public Passenger(String firstName, String lastName, int age, String gender, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
	}
/*
	@Override
	public String toString() {
		return String.format("Passenger[id=%s, firstName='%s', lastName='%s', age ='%d', gender='%s', phone='%s']", id, firstName, lastName, age, gender, phone);
	}
*/

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public String getPhone() {
		return phone;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
/*
 CREATE TABLE passenger(
   id INT NOT NULL AUTO_INCREMENT,
   firstname VARCHAR(20) NOT NULL,
   lastname VARCHAR(20) NOT NULL,
   age INT,
   gender VARCHAR(10),
   phone VARCHAR(10),
   PRIMARY KEY (id),
   UNIQUE(phone)
);
*/
