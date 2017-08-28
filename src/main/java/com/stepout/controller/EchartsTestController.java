package com.stepout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EchartsTestController {
	
	@RequestMapping("web/charts/line")
	public String line() {
		return "web/charts/line";
	}
}
