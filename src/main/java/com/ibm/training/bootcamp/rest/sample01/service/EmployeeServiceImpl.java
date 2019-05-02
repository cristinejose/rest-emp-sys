package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.dao.EmployeeDao;
import com.ibm.training.bootcamp.rest.sample01.dao.EmployeeJdbcDaoImpl;
import com.ibm.training.bootcamp.rest.sample01.domain.Employee;

public class EmployeeServiceImpl implements EmployeeService{
	
	EmployeeDao employeeDao;

	public EmployeeServiceImpl() {
		this.employeeDao = EmployeeJdbcDaoImpl.getInstance();
		
	}
	
	@Override
	public List<Employee> findAll() {
		return employeeDao.findAll();
	}
	
	
	@Override
	public List<Employee> findByName(String firstName, String lastName, String position) {
		return employeeDao.findByName(firstName, lastName, position);
	}

	@Override
	public void add(Employee employee) {
		if (validate(employee)) {
			employeeDao.add(employee);
		} else {
			throw new IllegalArgumentException("Fields firstName, lastName and position cannot be blank.");
		}
	}

	@Override
	public void upsert(Employee employee) {
		if (validate(employee)) {
			if(employee.getId() != null && employee.getId() >= 0) {
				employeeDao.update(employee);
			} else {
				employeeDao.add(employee);
			}
		} else {
			throw new IllegalArgumentException("Fields firstName, lastName and position cannot be blank.");
		}
	}

	@Override
	public void delete(Long id) {
		employeeDao.delete(id);
	}
	
	private boolean validate(Employee employee) {
		return !StringUtils.isAnyBlank(employee.getFirstName(), employee.getMiddleName(), employee.getLastName(), employee.getbDay(),
				employee.getPosition());
	}

}
