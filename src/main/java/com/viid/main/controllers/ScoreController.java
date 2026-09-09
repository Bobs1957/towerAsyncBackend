package com.viid.main.controllers;

import com.viid.main.db.DBManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ScoreController {

	@PostMapping("/score/set")
	public void score(
			@RequestParam String name,
			@RequestParam int score
	) {
		int currentScore = DBManager.instance.getScore(name);

		if (score > currentScore) {
			DBManager.instance.addPlayer(name, score);
		}
	}
	@GetMapping("/score/get")
	public int score(
			@RequestParam String name
	) {
		return DBManager.instance.getScore(name);
	}
	@GetMapping("/score/leader")
	public List<Map<String, Object>> score(@RequestParam(required = false) Integer count) {
		int c = 50;
		if (count != null){
			c = count;
		}
		return DBManager.instance.getLeaderboard(c);
	}
}