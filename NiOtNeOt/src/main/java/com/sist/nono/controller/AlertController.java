package com.sist.nono.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.sist.nono.model.Alert;
import com.sist.nono.service.AlertService;


@Controller
public class AlertController { 
	@Autowired
	private AlertService service;
	
	@GetMapping("/alertList")
	public void listAlert(Model model) {
		model.addAttribute("alertlist", service.findAllByCu_no(1));
	}
	
	@GetMapping("/alertLists")
	public ModelAndView listAlerts() {
		ModelAndView mav = new ModelAndView("redirect:/alertListss.html");
		return mav;
	}
	
	@GetMapping("/alerts/{al_no}")
	public String findById(@PathVariable int al_no, Model model) {
		model.addAttribute("alert",service.findById(al_no));
		return "alert/detail";
	}
}
