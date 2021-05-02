package com.etl.springbatchexcelcsvtodb.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etl.springbatchexcelcsvtodb.model.User;
import com.etl.springbatchexcelcsvtodb.repository.UserRepository;

@Component
public class DatabaseWriter implements ItemWriter<User> {

	private static final String EMPTY = "  ";
	@Autowired
	private UserRepository userRepository;

	@Override
	public void write(List<? extends User> items) throws Exception {
		System.out.println(":: saving data ::");
		userRepository.saveAll(items);
		items.stream().forEach(e -> System.out.println("Saved For Users :: "+e.getId()+ EMPTY+e.getName()));

	}
}
