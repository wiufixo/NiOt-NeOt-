package com.sist.nono.controller.api;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.nono.model.Customer;
import com.sist.nono.service.AddressService;
import com.sist.nono.service.CustomerService;
import com.sist.nono.service.FollowService;
import com.sist.nono.service.LoginListService;
import com.sist.nono.service.TransHistoryService;
import com.sist.nono.service.WishService;

import lombok.Setter;

@RestController
@Setter
public class CustomersAPIController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	FollowService followService;
	
	@Autowired
	LoginListService loginListService;
	
	@Autowired
	TransHistoryService transHistoryService;
	
	@Autowired
	WishService wishService;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@PostMapping("/customer/pwdSend")
	public String pwdSend(@RequestParam("cu_id") String cu_id) {
		javaMailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException {
				Customer cu= customerService.findByCu_id(cu_id);
				String cu_email=cu.getCu_email();
				String cu_pwd=cu.getCu_pwd();
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setFrom("sonm2468@gmail.com");
				message.setTo(cu_email);
				message.setSubject("nono 비밀번호 발송");
				message.setText("<h1>"+cu_pwd+"</h1>", true);
			}
		});
		return "OK";
	}
	
	@PostMapping("/customer/checkSend")
	public String checkSend(@RequestParam("cu_email") String cu_email) {
		Random r=new Random();
		String check_num="";

		for(int i=0;i<4;i++) {
			String num=Integer.toString(r.nextInt(9));
			check_num+=num;
		}
		
		final String a=check_num;
		
		javaMailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setFrom("sonm2468@gmail.com");
		//		message.setTo(cu_email);
				message.setTo("sonm446@naver.com");
				message.setSubject("nono 인증번호 발송");
				message.setText("<h1>"+a+"</h1>", true);
			}
		});
		return a;
	}
}