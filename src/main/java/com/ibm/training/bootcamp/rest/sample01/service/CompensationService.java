package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.Compensation;
	

public interface CompensationService {
	
		public List<Compensation> findAll();
		
		public List<Compensation> findByMonth(String month);
	
		public void add(Compensation compensation);
	
		public void upsert(Compensation compensation);
	
		public void delete(Long id_comp);
	
	
}
