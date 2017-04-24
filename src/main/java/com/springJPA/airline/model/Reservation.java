package com.springJPA.airline.model;

import java.io.Serializable;

import javax.persistence.Column;
//import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable{

	private static final long serialVersionUID = -8261334108709924377L;
	
	@Id
	@Column(name = "orderNumber")
	private String orderNumber;

}
