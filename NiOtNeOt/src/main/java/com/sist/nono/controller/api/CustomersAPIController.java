package com.sist.nono.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sist.nono.model.Board;
import com.sist.nono.model.Customer;
import com.sist.nono.model.Feed;
import com.sist.nono.model.Follow;
import com.sist.nono.model.Product;
import com.sist.nono.model.TransHistory;
import com.sist.nono.model.Wish;
import com.sist.nono.service.AddressService;
import com.sist.nono.service.BoardService;
import com.sist.nono.service.CustomerService;
import com.sist.nono.service.FeedService;
import com.sist.nono.service.FollowService;
import com.sist.nono.service.LoginListService;
import com.sist.nono.service.ProductService;
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
	BoardService boardService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	FeedService feedService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@PostMapping("/customer/insertFollow")
	@Transactional
	public String insertFollow(Authentication auth, @RequestParam("cu_no") int cu_no,@RequestParam("user_no") int user_no) {
		if(auth.getName()==null) {
			return "/index";
		}
		
		Follow fo=new Follow();
		fo.setFollowed(user_no);
		fo.setFollower(cu_no);
		followService.saveFollow(fo);
		
		return "OK";
	}
	
	@PostMapping("/customer/deleteFollow")
	@Transactional
	public void deleteFollow(@RequestParam("cu_no") int cu_no,@RequestParam("user_no") int user_no) {
		followService.deleteFollow(followService.findFo_no(cu_no, user_no));
	}
	
	@PostMapping("customer/transOK")
	public void transOK(HttpServletRequest request) {
		TransHistory t=new TransHistory();
		t.setPr_no(Integer.parseInt(request.getParameter("pr_no")));
		t.setBuy_no(Integer.parseInt(request.getParameter("buy_no")));
		t.setSell_no(Integer.parseInt(request.getParameter("sell_no")));
		t.setSell_score(5);
		t.setBuy_score(5);
		
		transHistoryService.saveTransHistory(t);
	}
	
	
	@PostMapping("customer/transScore")
	public void transScore(HttpServletRequest request) {
		TransHistory t=transHistoryService.findById(Integer.parseInt(request.getParameter("tr_no")));
		t.setBuy_score(Integer.parseInt(request.getParameter("buy_score")));
		t.setSell_score(Integer.parseInt(request.getParameter("sell_score")));
		
	}
	
	@PostMapping("customer/wishSave")
	public void wishSave(HttpServletRequest request) {
		Wish wish = new Wish();
		
		//getorelse 써줘야함
		wish.setProduct((Product) productService.findById(Integer.parseInt(request.getParameter("pr_no")))); 
		wish.setCustomer(customerService.findById(Integer.parseInt(request.getParameter("cu_no")))); 
		
		wishService.saveWish(wish);
	}

	
	@PostMapping("/customer/pwdSend")
	public String pwdSend(@RequestParam("cu_id") String cu_id) {
		Random r=new Random();
		String check_num="";
		
		for(int i=0;i<4;i++) {
			String num=Integer.toString(r.nextInt(9));
			check_num+=num;
		}

		Customer cu=customerService.findByCu_id(cu_id);
		cu.setCu_pwd(check_num);
		customerService.saveCustomer(cu);
		
		final String a=check_num;
		
		
		javaMailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException {
				String cu_email=cu.getCu_email();
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setFrom("sonm2468@gmail.com");
				message.setTo(cu_email);
				
				message.setSubject("nono 비밀번호 발송");
				message.setText("<h1>"+a+"</h1>", true);
			}
		});
		return "OK";
	}
	
	@GetMapping("customer/transPaging")
	public ArrayList<TransHistory> transPaging(Model model,HttpServletRequest request) {
		int page=Integer.parseInt(request.getParameter("page"));
		int count=page*12;
		int cu_no=Integer.parseInt(request.getParameter("cu_no"));
		ArrayList<TransHistory> list=new ArrayList<TransHistory>();
		
		List<TransHistory> list1=transHistoryService.findAllByCu_no(cu_no);
		for(int i=count-12;i<count;i++) {
			//i가 가진 값보다 많아졌을 경우 오류 방지
			if(i>=list1.size()) {
				break;
			}
			list.add(list1.get(i));
		}
		return list;
	}
	
	@GetMapping("customer/wishPaging")
	public ArrayList<Wish> wishPaging(Model model,HttpServletRequest request) {
		int page=Integer.parseInt(request.getParameter("page"));
		int count=page*12;
		int cu_no=Integer.parseInt(request.getParameter("cu_no"));
		ArrayList<Wish> list=new ArrayList<Wish>();
		
		List<Wish> list1=wishService.findAllByCu_no(cu_no);
		for(int i=count-12;i<count;i++) {
			//i가 가진 값보다 많아졌을 경우 오류 방지
			if(i>=list1.size()) {
				break;
			}
			list.add(list1.get(i));
		}
		return list;
	}
	
	@GetMapping("customer/productPaging")
	public ArrayList<Product> productPaging(Model model,HttpServletRequest request) {
		int page=Integer.parseInt(request.getParameter("page"));
		int count=page*12;
		int cu_no=Integer.parseInt(request.getParameter("cu_no"));
		ArrayList<Product> list=new ArrayList<Product>();
		
		List<Product> list1=productService.findAllByCu_no(cu_no);
		for(int i=count-12;i<count;i++) {
			//i가 가진 값보다 많아졌을 경우 오류 방지
			if(i>=list1.size()) {
				break;
			}
			list.add(list1.get(i));
		}
		return list;
	}
	
	@GetMapping("customer/feedLoad")
	public ArrayList<Feed> feedLoad(Model model,HttpServletRequest request) {
		int page=Integer.parseInt(request.getParameter("page"));
		//10개씩 호출
		int count=page*10;
		int cu_no=Integer.parseInt(request.getParameter("customer"));
		ArrayList<Feed> list=new ArrayList<Feed>();
		
		List<Feed> list1=feedService.findByCu_no(cu_no);
		for(int i=count-10;i<count;i++) {
			//i가 가진 값보다 많아졌을 경우 오류 방지
			if(i>=list1.size()) {
				break;
			}
			//정해진 번호의 유저정보 넣기
			list.add(list1.get(i));
		}
		return list;
	}
	
	@GetMapping("customer/boardLoad")
	public ArrayList<Board> boardLoad(Model model,HttpServletRequest request) {
		int page=Integer.parseInt(request.getParameter("page"));
		//10개씩 호출
		int count=page*10;
		int cu_no=Integer.parseInt(request.getParameter("customer"));
		ArrayList<Board> list=new ArrayList<Board>();
		
		List<Board> list1=boardService.findAllByCu_no(cu_no);
		for(int i=count-10;i<count;i++) {
			//i가 가진 값보다 많아졌을 경우 오류 방지
			if(i>=list1.size()) {
				break;
			}
			//정해진 번호의 유저정보 넣기
			list.add(list1.get(i));
		}
		return list;
	}
	
	@GetMapping("customer/followingLoad")
	public ArrayList<Customer> followingLoad(Model model,HttpServletRequest request) {
		int page=Integer.parseInt(request.getParameter("page"));
		//10개씩 호출
		int count=page*10;
		int cu_no=Integer.parseInt(request.getParameter("customer"));
		ArrayList<Customer> list=new ArrayList<Customer>();
		
		List<Follow> list1=followService.findByFollower(cu_no);
		
		for(int i=count-10;i<count;i++) {
			//i가 가진 값보다 많아졌을 경우 오류 방지
			if(i>=list1.size()) {
				break;
			}
			//정해진 번호의 유저정보 넣기
			list.add(customerService.findById(list1.get(i).getFollowed()));
		}
		return list;
	}
	
	@GetMapping("customer/followerLoad")
	public ArrayList<Customer> followLoad(Model model,HttpServletRequest request) {
		int page=Integer.parseInt(request.getParameter("page"));
		//10개씩 호출
		int count=page*10;
		int cu_no=Integer.parseInt(request.getParameter("customer"));
		ArrayList<Customer> list=new ArrayList<Customer>();
		
		List<Follow> list1=followService.findByFollowed(cu_no);
		
		
		for(int i=count-10;i<count;i++) {
			//i가 가진 값보다 많아졌을 경우 오류 방지
			if(i>=list1.size()) {
				break;
			}
			//정해진 번호의 유저정보 넣기
			list.add(customerService.findById(list1.get(i).getFollower()));
		}
		
		return list;
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
				message.setTo(cu_email);
				message.setSubject("nono 인증번호 발송");
				message.setText("<h1>"+a+"</h1>", true);
			}
		});
		return a;
	}
}