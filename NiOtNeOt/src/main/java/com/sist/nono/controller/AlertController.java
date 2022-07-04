package com.sist.nono.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sist.nono.service.AlertService;


@Controller
public class AlertController { 
	@Autowired
	private AlertService service;
	
	@GetMapping("/alertList")
	public void listGoods(Model model) {
		model.addAttribute("alertList", service.findAllByCu_no(1));
	}
	
	@GetMapping("/alert/{al_no}")
	public String findById(@PathVariable int al_no, Model model) {
		model.addAttribute("alert",service.findById(al_no));
		return "alert/detail";
	}
}
