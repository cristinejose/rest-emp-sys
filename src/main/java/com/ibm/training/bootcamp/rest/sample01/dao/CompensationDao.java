package com.ibm.training.bootcamp.rest.sample01.dao;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.Compensation;


public interface CompensationDao {

	public List<Compensation> findAll();
	
	public List<Compensation> findByMonth(String month);

	public void add(Compensation compensation);

	public void update(Compensation compensation);

	public void delete(Long id);

}
