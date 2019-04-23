package com.ibm.training.bootcamp.rest.sample01.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.domain.User;

public class UserHashMapDaoImpl implements UserDao {

	static private UserHashMapDaoImpl INSTANCE;
	static private final Map<Long, User> USER_STORE;
	static private long id = 0;

	static {
		USER_STORE = new HashMap<>();
		User user1 = new User(id++, "Tony", "Stark", "iron man");
		User user2 = new User(id++, "Steve", "Rogers", "captain america");
		User user3 = new User(id++, "Peter", "Parker", "spiderman");
		USER_STORE.put(user1.getId(), user1);
		USER_STORE.put(user2.getId(), user2);
		USER_STORE.put(user3.getId(), user3);
	}

	private UserHashMapDaoImpl() {
		
	}
	
	static public UserHashMapDaoImpl getInstance( ) {
		
		UserHashMapDaoImpl instance;
		if (INSTANCE != null) {
			instance = INSTANCE;
		} else {
			instance = new UserHashMapDaoImpl();
			INSTANCE = instance;
		}
		
		return instance;
	}
	
	@Override
	public List<User> findAll() {
		return new ArrayList<User>(USER_STORE.values());
	}

	

	@Override
	public List<User> findByName(String firstName, String lastName, String position) {
		List<User> users = USER_STORE.values().stream()
				.filter(user -> StringUtils.isBlank(firstName) || user.getFirstName().equalsIgnoreCase(firstName))
				.filter(user -> StringUtils.isBlank(lastName) || user.getLastName().equalsIgnoreCase(lastName))
				.filter(user -> StringUtils.isBlank(position) || user.getLastName().equalsIgnoreCase(position))
				.collect(Collectors.toList());
		
//		List<User> users = new ArrayList<>(USER_STORE.values());
//
//		List<User> results = new ArrayList<>();
//		for (User user : users) {
//			if ( (StringUtils.isBlank(firstName) || user.getFirstName().equalsIgnoreCase(firstName)) 
//					&& (StringUtils.isBlank(lastName) || user.getLastName().equalsIgnoreCase(lastName))) {
//				results.add(user);
//			}
//		}
		
		//return results;
		return users;
	}

	@Override
	public void add(User user) {
		if (user != null && user.getId() == null) {
			user.setId(id++);
			USER_STORE.put(user.getId(), user);
		}
	}

	@Override
	public void update(User user) {
		if (user != null && user.getId() != null) {
			USER_STORE.put(user.getId(), user);
		}
	}

	@Override
	public void delete(Long id) {
		if (id != null) {
			USER_STORE.remove(id);
		}
	}

}
