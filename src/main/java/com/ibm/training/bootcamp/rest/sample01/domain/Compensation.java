package com.ibm.training.bootcamp.rest.sample01.domain;

public class Compensation {

	Long id_comp;
	private String month;
	private String salary;
	private String bonus;
	private String commission;

	public Compensation() {

	}

	public Compensation(String month, String salary, String bonus, String commission) {
		this(null, month, salary, bonus, commission);

	}

	public Compensation(Long id_comp, String month, String salary, String bonus, String commission) {
		this.id_comp = id_comp;
		this.month = month;
		this.salary = salary;
		this.bonus = bonus;
		this.commission = commission;
	}

	public Long getId_comp() {
		return id_comp;
	}

	public void setId_comp(Long id_comp) {
		this.id_comp = id_comp;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

}
