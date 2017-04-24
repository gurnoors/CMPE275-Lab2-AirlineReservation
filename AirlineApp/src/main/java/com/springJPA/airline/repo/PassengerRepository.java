package com.springJPA.airline.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

//import java.util.List;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.springJPA.airline.model.Passenger;


public interface PassengerRepository extends CrudRepository<Passenger, String>{
	//List<Passenger> findByLastName(String lastName);
	
	@Transactional
    @Modifying(clearAutomatically = true)
	@Query("update Passenger p set p.firstName=?1, p.lastName=?2, p.age=?3, p.gender=?4, p.phone=?5 where p.id=?6")
	void updatePassengerDetails(String firstname, String lastname, int age, String gender, String phone, String id);
	
}