package com.jenkis.rest.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jenkins/rest")
public class RestControllerExample {

	@GetMapping("/name")
	public String getName() {
		return "Anil Kamatham";
	}
}
