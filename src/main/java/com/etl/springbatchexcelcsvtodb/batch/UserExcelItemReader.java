package com.etl.springbatchexcelcsvtodb.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.etl.springbatchexcelcsvtodb.model.User;

@Component
@StepScope
public class UserExcelItemReader extends PoiItemReader<User> {

	private static final String SRC_MAIN_RESOURCES_EMPLOYEES_XLS = "src/main/resources/users.xlsx";

	public UserExcelItemReader() {
		setLinesToSkip(1);
		setResource(new FileSystemResource(SRC_MAIN_RESOURCES_EMPLOYEES_XLS));
		setRowMapper(excelRowMapper());
	}

	private RowMapper<User> excelRowMapper() {
		return new UserExcelRowMapper();
	}
}
