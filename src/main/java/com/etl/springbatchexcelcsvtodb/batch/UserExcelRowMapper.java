package com.etl.springbatchexcelcsvtodb.batch;

import java.util.Date;

import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

import com.etl.springbatchexcelcsvtodb.model.User;

public class UserExcelRowMapper implements RowMapper<User> {

	private static final String _0 = ".0";

	@Override
	public User mapRow(RowSet rowSet) throws Exception {
		User user = new User();
		user.setId(Long.valueOf(rowSet.getColumnValue(0).trim().replace(_0, "")));
		user.setName(rowSet.getColumnValue(1));
		user.setAge(Integer.parseInt(rowSet.getColumnValue(2).trim().replace(_0, "")));
		user.setDept(rowSet.getColumnValue(3).replace(_0, ""));
		user.setDate(new Date());
		return user;
	}

}
