package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.dao.CompensationDao;
import com.ibm.training.bootcamp.rest.sample01.dao.CompensationJdbcDaoImpl;
import com.ibm.training.bootcamp.rest.sample01.domain.Compensation;
import com.ibm.training.bootcamp.rest.sample01.domain.Employee;

public class CompensationServiceImpl implements CompensationService {

	CompensationDao compensationDao;

	public CompensationServiceImpl() {
		this.compensationDao = CompensationJdbcDaoImpl.getInstance();

	}

	@Override
	public List<Compensation> findAll() {
		return compensationDao.findAll();
	}

	@Override
	public List<Compensation> findByMonth(String month) {
		return compensationDao.findByMonth(month);
	}

	@Override
	public void add(Compensation compensation) {
		if (validate(compensation)) {
			compensationDao.add(compensation);
		}
	}

	@Override
	public void upsert(Compensation compensation) {
		if (validate(compensation)) {
			if (compensation.getId_comp() != null && compensation.getId_comp() >= 0) {
				compensationDao.update(compensation);
			} else {
				compensationDao.add(compensation);
			}
		}
	}

	@Override
	public void delete(Long id_comp) {
		compensationDao.delete(id_comp);
	}

	private boolean validate(Compensation compensation) {
		return !StringUtils.isAnyBlank(compensation.getMonth(), compensation.getSalary(), compensation.getBonus(),
				compensation.getCommission());
	}

}
