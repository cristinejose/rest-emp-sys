package com.ibm.training.bootcamp.rest.sample01.dao;

import java.util.List;
import com.ibm.training.bootcamp.rest.sample01.domain.Employee;

public interface EmployeeDao {
	
	public List<Employee> findAll();
	
	public List<Employee> findByName(String firstName, String lastName, String position);
	
	public void add(Employee employee);
	
	public void update(Employee employee);
	
	public void delete(Long id);

}
