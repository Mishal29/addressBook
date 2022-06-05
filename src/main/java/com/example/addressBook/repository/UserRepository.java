package com.example.addressBook.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	
	@Autowired
	private JdbcTemplate jdbc;
	
	//ユーザIDをもとに対応するパスワードを取得
	public Map<String, Object> getPw(String userId) {
		String sql = "SELECT pw FROM user WHERE user_id=?";
		Map<String, Object> result;
		
		try {
			result = jdbc.queryForMap(sql, userId);
			
		} catch (EmptyResultDataAccessException e) {
			result = new HashMap<String, Object>();
			result.put("pw", null);
		}
		
		return result;
	}
}
