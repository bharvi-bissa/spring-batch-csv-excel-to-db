package com.etl.springbatchexcelcsvtodb.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.etl.springbatchexcelcsvtodb.model.User;

@Component
public class Processor implements ItemProcessor<User, User> {

	static Map<String, String> deptMap = new HashMap<>();

	static {
		deptMap.put("1", "IT");
		deptMap.put("2", "HR");
		deptMap.put("3", "Accounts");
	}

	@Override
	public User process(User user) throws Exception {
		user.setDept(deptMap.get(user.getDept()));
		user.setDate(new Date());
		return user;
	}
	

}
