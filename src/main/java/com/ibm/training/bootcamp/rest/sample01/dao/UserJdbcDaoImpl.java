package com.ibm.training.bootcamp.rest.sample01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.jdbc.JDBCDataSource;

import com.ibm.training.bootcamp.rest.sample01.domain.User;

public class UserJdbcDaoImpl implements UserDao {

	private static UserJdbcDaoImpl INSTANCE;

	private JDBCDataSource dataSource;

	static public UserJdbcDaoImpl getInstance() {

		UserJdbcDaoImpl instance;
		if (INSTANCE != null) {
			instance = INSTANCE;
		} else {
			instance = new UserJdbcDaoImpl();
			INSTANCE = instance;
		}

		return instance;
	}

	private UserJdbcDaoImpl() {
		init();
	}

	private void init() {
		dataSource = new JDBCDataSource();
		dataSource.setDatabase("jdbc:hsqldb:mem:USER");
		dataSource.setUser("username");
		dataSource.setPassword("password");

		createUserTable();
		insertInitUsers();

	}

	private void createUserTable() {
		String createSql = "CREATE TABLE USERS " + "(id INTEGER IDENTITY PRIMARY KEY, " + " firstname VARCHAR(255), "
				+ " lastname VARCHAR(255)," + " position VARCHAR(255))";

		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(createSql);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void insertInitUsers() {

		add(new User("Tony","Stark","iron man"));
		add(new User("Steve","Rogers", "captain america"));
		add(new User("Peter","Parker", "spiderman"));
		add(new User("Natasha","Romanov", "captain marvel"));
	}

	@Override
	public List<User> findAll() {

		return findByName(null, null, null);
	}



	@Override
	public List<User> findByName(String firstName, String lastName, String position) {
		List<User> users = new ArrayList<>();

		String sql = "SELECT id, firstname, lastname, position FROM USERS WHERE firstname LIKE ? AND lastname LIKE ? AND position LIKE ?";
//		String sql1 = "SELECT id, firstname, lastname, position FROM USERS WHERE position LIKE ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, createSearchValue(firstName));
			ps.setString(2, createSearchValue(lastName));
			ps.setString(3, createSearchValue(position));
			
			ResultSet results = ps.executeQuery();

			while (results.next()) {
				User user = new User(Long.valueOf(results.getInt("id")), results.getString("firstname"),
						results.getString("lastname"), results.getString("position"));
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return users;
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
	public void add(User user) {
		
		String insertSql = "INSERT INTO USERS (firstname, lastname, position) VALUES (?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {

			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getPosition());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(User user) {
		String updateSql = "UPDATE users SET firstname = ?, lastname = ?, position = ? WHERE id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getPosition());
			ps.setLong(4, user.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Long id) {
		String updateSql = "DELETE FROM users WHERE id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}