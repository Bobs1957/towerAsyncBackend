package com.viid.main.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Component
public class DBManager {

	public static DBManager instance;
	private final JdbcTemplate jdbcTemplate;

	public DBManager(JdbcTemplate jdbcTemplate) {
		instance = this;
		this.jdbcTemplate = jdbcTemplate;
	}

	public void execute(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void addPlayer(String name, int score){
		String sql = """
        INSERT INTO players (username, score) 
        VALUES (?, ?) 
        ON CONFLICT (username) 
        DO UPDATE SET score = ?
    	""";
		System.out.println("Игроку "+name+ " установленн счёт "+ score);
		jdbcTemplate.update(sql, name, score, score);
	}

	public List<Map<String, Object>> query(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> getLeaderboard(int count) {
		String sql = "SELECT username, score FROM players ORDER BY score DESC LIMIT ?";
		return jdbcTemplate.queryForList(sql, count);
	}
	public int getScore(String name) {
		String sql = "SELECT score FROM players WHERE username = ?";
		try {
			return jdbcTemplate.queryForObject(sql, Integer.class, name);
		} catch (Exception e) {
			return 0; // или выбросить ошибку
		}
	}

	public int update(String sql, Object... params) {
		return jdbcTemplate.update(sql, params);
	}
}
