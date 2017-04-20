package edu.sjsu.cmpe275.lab2.entities;

import java.util.List;

public class Reservation {
    private String orderNumber;
    private Passenger passenger;
    private int price; // sum of each flight’s price.
    private List<Flight> flights;
}
