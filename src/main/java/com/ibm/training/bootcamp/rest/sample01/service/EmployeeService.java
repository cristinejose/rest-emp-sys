package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.Employee;

public interface EmployeeService {

	public List<Employee> findAll();
	
	public List<Employee> findByName(String firstName, String lastName, String position);
	
	public void add(Employee employee);
	
	public void upsert(Employee employee);
	
	public void delete(Long id);

}
