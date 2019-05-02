package com.ibm.training.bootcamp.rest.sample01.dao;

import org.hsqldb.jdbc.JDBCDataSource;

public abstract class employeeSystem {

	public JDBCDataSource dataSource;

	public void init() {
		dataSource = new JDBCDataSource();
		dataSource.setDatabase("jdbc:hsqldb:hsql://localhost/compdb");
		dataSource.setUser("SA");

	}
}
