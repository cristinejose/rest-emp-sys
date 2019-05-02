package com.ibm.training.bootcamp.rest.sample01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


import com.ibm.training.bootcamp.rest.sample01.domain.Employee;

public class EmployeeJdbcDaoImpl extends employeeSystem implements EmployeeDao {

	private static EmployeeJdbcDaoImpl INSTANCE;


	static public EmployeeJdbcDaoImpl getInstance() {

		EmployeeJdbcDaoImpl instance;
		if (INSTANCE != null) {
			instance = INSTANCE;
		} else {
			instance = new EmployeeJdbcDaoImpl();
			INSTANCE = instance;
		}

		return instance;
	}

	private EmployeeJdbcDaoImpl() {
		init();
	}



	@Override
	public List<Employee> findAll() {

		return findByName(null, null, null);
	}



	@Override
	public List<Employee> findByName(String firstName, String lastName, String position) {
		List<Employee> employees = new ArrayList<>();

		String sql = "SELECT id, firstname, middlename, lastname, bday, position FROM EMPLOYEES WHERE firstname LIKE ? AND lastname LIKE ? AND position LIKE ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, createSearchValue(firstName));
			ps.setString(2, createSearchValue(lastName));
			ps.setString(3, createSearchValue(position));
			
			ResultSet results = ps.executeQuery();

			while (results.next()) {
				Employee employee = new Employee(Long.valueOf(results.getInt("id")), results.getString("firstname"),
					results.getString("middlename"), results.getString("lastname"), results.getString("bday"), results.getString("position"));
				employees.add(employee);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return employees;
	}

	private String createSearchValue(String string) {
		
		String value;
		
		if (StringUtils.isBlank(string)) {
			value = "%";
		} else {
			value = string;
		}
		
		return value;
	}
	
	@Override
	public void add(Employee employee) {
		
		String insertSql = "INSERT INTO EMPLOYEES (firstname, middlename, lastname, bday, position) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {

			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getMiddleName());
			ps.setString(3, employee.getLastName());
			ps.setString(4, employee.getbDay());
			ps.setString(5, employee.getPosition());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Employee employee) {
		String updateSql = "UPDATE employees SET firstname = ?, middlename = ?, lastname = ?, bday = ?, position = ? WHERE id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getMiddleName());
			ps.setString(3, employee.getLastName());
			ps.setString(4, employee.getbDay());
			ps.setString(5, employee.getPosition());
			ps.setLong(6, employee.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Long id) {
		String updateSql = "DELETE FROM employees WHERE id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
