package com.ibm.training.bootcamp.rest.sample01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.domain.Compensation;

public class CompensationJdbcDaoImpl extends employeeSystem implements CompensationDao {

	private static CompensationJdbcDaoImpl INSTANCE;

	static public CompensationJdbcDaoImpl getInstance() {

		CompensationJdbcDaoImpl instance;
		if (INSTANCE != null) {
			instance = INSTANCE;
		} else {
			instance = new CompensationJdbcDaoImpl();
			INSTANCE = instance;
		}

		return instance;
	}

	private CompensationJdbcDaoImpl() {
		init();
	}

	@Override
	public List<Compensation> findAll() {

		return findByMonth(null);
	}

	@Override
	public List<Compensation> findByMonth(String month) {
		List<Compensation> compensations = new ArrayList<>();

		String sql = "SELECT id_comp, month, salary, bonus, commission FROM COMPENSATIONS WHERE month LIKE ? ";
		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, createSearchValue(month));

			ResultSet results = ps.executeQuery();

			while (results.next()) {
				Compensation compensation = new Compensation(Long.valueOf(results.getInt("id_comp")),
						results.getString("month"), results.getString("salary"), results.getString("bonus"),
						results.getString("commission"));
				compensations.add(compensation);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return compensations;
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
	public void add(Compensation compensation) {

		String insertSql = "INSERT INTO COMPENSATIONS (id_comp, month, salary, bonus, commission) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {

			ps.setLong(1, compensation.getId_comp());
			ps.setString(2, compensation.getMonth());
			ps.setString(3, compensation.getSalary());
			ps.setString(4, compensation.getBonus());
			ps.setString(5, compensation.getCommission());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Compensation compensation) {
		String updateSql = "UPDATE compensations SET id_comp = ?, month = ?, salary = ?, bonus = ?, commission = ? WHERE id_comp = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1, compensation.getId_comp());
			ps.setString(2, compensation.getMonth());
			ps.setString(3, compensation.getSalary());
			ps.setString(4, compensation.getBonus());
			ps.setString(5, compensation.getCommission());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Long id_comp) {
		String updateSql = "DELETE FROM compensations WHERE id_comp = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1, id_comp);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
