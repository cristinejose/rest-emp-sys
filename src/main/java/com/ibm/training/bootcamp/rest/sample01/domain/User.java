package com.ibm.training.bootcamp.rest.sample01.domain;

import java.sql.Date;

public class User {

	Long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String bDay;
	private String position;
	
	public User() {
		
	}
	
	public User(String firstName, String middleName, String lastName, String bDay, String position) {
		this(null, firstName, middleName, lastName, bDay, position);
	}

	public User(Long id, String firstName, String middleName, String lastName, String bDay, String position) {
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.bDay = bDay;
		this.position = position;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getbDay() {
		return bDay;
	}

	public void setbDay(String bDay) {
		this.bDay = bDay;
	}
	
}
