package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import com.razorpay.*;

import org.hibernate.internal.build.AllowSysOut;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ModuleRepository;
import com.smart.dao.MyOrderRepository;
import com.smart.dao.UserRepository;
import com.smart.dao.submoduleRepository;
import com.smart.entities.MyOrder;
import com.smart.entities.SubModule;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.entities.Module;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private submoduleRepository submoduleRepo;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private MyOrderRepository myOrderRepository;

	// method for adding common data for response

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("USERNAME" + userName);

		// get the user using userName (Email)

		User user = userRepository.getUserByUserName(userName);

		System.out.println("USER" + user);

		model.addAttribute("user", user);

	}

	// dashboard for home

	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {

		return "normal/user_dashboard";
	}

	// open Add module handler
	@GetMapping("/add-module")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Module");
		

		model.addAttribute("module", new Module());
		return "normal/add_module_form";
	}

	// view module details
	@GetMapping("/viewuserdata/{cId}")
	public String view(Model model, @PathVariable("cId") Integer cId) {

		Module module = this.moduleRepository.findById(cId).orElse(new Module());

		model.addAttribute("module", module);
		return "normal/view_individual_details";
	}

	@PostMapping("/save_subData")
	public String save_subData(@ModelAttribute Module module, Model m, @ModelAttribute("workitem") String workitem,
			@ModelAttribute("date") String date, @ModelAttribute("remarks") String remarks,
			@ModelAttribute("cId") Integer cId, HttpSession session) throws ParseException {

		SubModule s = new SubModule();
		/*
		 * DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); Date d =
		 * format.parse(date);
		 */
		s.setModules(module);
		s.setsRemarks(remarks);
		Date d = new Date();
		s.setStartdate(d);
		s.setWorkitem(workitem);

		submoduleRepo.save(s);
		
		return viewSubModulesByCid(1,cId, m);
	}

	@GetMapping("/viewSubModulesByCid/{page}/{cId}")
	public String viewSubModulesByCid(@PathVariable("page")Integer page,@PathVariable("cId") Integer cId,Model m) {

		List<SubModule> list = submoduleRepo.findBycId(cId);

		Optional<Module> moduleOptional = this.moduleRepository.findById(cId);
		
		
		Pageable pageable = PageRequest.of(page, 5);
		

		/*Pageable pageable = PageRequest.of(page, 10);
		 * Page<Module> modules = this.moduleRepository.findModuleByUser(user.getId(),
		 * pageable);
		 * 
		 * m.addAttribute("modules", modules); 
		 * m.addAttribute("currentPage", page);
		 * m.addAttribute("totalPages", modules.getTotalPages());
		 */
		
		
		m.addAttribute("cId", cId);
		
		Page<Module> modules = this.moduleRepository.findModuleByUser(141, pageable);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", modules.getTotalPages());
		
		
		Module module = moduleOptional.get();
		m.addAttribute("module", module); // mob
		
		
			m.addAttribute("modules", list);// sub	
			m.addAttribute("listsize",list.size() );
		
		
		
		return "normal/view_individual_modules";
	}

		

	@GetMapping("/view-sub-module/{sid}")
	public String viewSubModule(@PathVariable("sid") Integer sid, Model m, Module module) {

		Optional<SubModule> subopt = submoduleRepo.findById(sid);
		SubModule modules = subopt.get();

		m.addAttribute("module", module);
		m.addAttribute("modules", modules);

		return "normal/update_submodule";

	}

	@PostMapping("/update-sub-module")
	public String updateSubModule(@ModelAttribute("sId") Integer sId, @ModelAttribute("enddate") String enddate,
			Model m, Module module) throws ParseException {
		System.out.println(enddate);//change

		/*
		 * DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
		 * Locale.ENGLISH); Date parsestartdate = format.parse(startdate);
		 */

		/*
		 * SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		 * SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd"); Date
		 * parsedDate = inputFormat.parse(enddate); //change
		 */
		SubModule sm = submoduleRepo.getById(sId);
		Date d = new Date();
		
		sm.setEnddate(d);
		int cid = sm.getModules().getcId();

		//sm.setStartdate(parsedDate);
		//sm.setEnddate(parsedDate);
		submoduleRepo.save(sm);
		return viewSubModulesByCid(1,cid, m);

	}

	@PostMapping("/process-module")
	public String processModule(@ModelAttribute Module module, Principal principal, HttpSession session) {

		try {

			String name = principal.getName();
			User user = this.userRepository.getUserByUserName(name);

			module.setUser(user);

			user.getModules().add(module);

			this.userRepository.save(user);

			System.out.println("DATA coming" + module);

			System.out.println("Added to database");

			// message success.....

			session.setAttribute("message", new Message("Your Module is added !! Add more..", "success"));

		} catch (Exception e) {
			System.out.println("Error" + e.getMessage());
			e.printStackTrace();

			// error message...

			session.setAttribute("message", new Message("Something went wrong !! Try again..", "danger"));

		}

		return "normal/add_module_form";
	}

	// show modules handler

	// per page = 5[n]
	// current page = 0 [page]

	@GetMapping("/show-modules/{page}")
	public String showModules(@PathVariable("page") Integer page, Model m, Principal principal) {

		m.addAttribute("title", "Show user Modules");

		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);

		
		Pageable pageable = PageRequest.of(page, 10);

		Page<Module> modules = this.moduleRepository.findModuleByUser(user.getId(), pageable);

		m.addAttribute("modules", modules);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", modules.getTotalPages());

		return "normal/show_modules";
	}

	// showing particular module details

	@RequestMapping("/{cid}/module")
	public String showModuleDetail(@PathVariable("cid") Integer cId, Model model, Principal principal) {

		System.out.println("CID" + cId);

		Optional<Module> moduleOptional = this.moduleRepository.findById(cId);
		Module module = moduleOptional.get();

		//
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);

		if (user.getId() == module.getUser().getId()) {
			model.addAttribute("module", module);
			model.addAttribute("title", module.getName());
		}
		return "normal/module_detail";
	}

	// delete module handler

	@GetMapping("/delete/{cid}")
	public String deleteModule(@PathVariable("cid") Integer cId, Model model, HttpSession session,
			Principal principal) {

		Optional<Module> moduleOptional = this.moduleRepository.findById(cId);
		Module module = moduleOptional.get();

		/*
		 * Module module = this.moduleRepository.findById(cId).get();
		 */
		System.out.println("Module" + module.getcId());

		module.setUser(null);
		this.moduleRepository.delete(module);

		User user = this.userRepository.getUserByUserName(principal.getName());

		user.getModules().remove(module);

		this.userRepository.save(user);

		session.setAttribute("message", new Message("Module deleted Successfully", "success"));

		return "redirect:/user/show-modules/0";
	}

	// open update form handler
	@PostMapping("/update-module/{cid}")
	public String updateForm(@PathVariable("cid") Integer cId, Model m) {

		m.addAttribute("title", "Update Module");
		Module module = this.moduleRepository.findById(cId).get();

		m.addAttribute("module", module);

		return "normal/update_form";

	}

	// update module handler
	@RequestMapping(value = "/process-update", method = RequestMethod.POST)
	public String updateHandler(@ModelAttribute Module module, @RequestParam("profileImage") MultipartFile file,
			Model m, HttpSession session, Principal principal) {

		try {

			// old contact details

			Module oldModuleDetail = this.moduleRepository.findById(module.getcId()).get();

			// image...

			if (!file.isEmpty()) {
				// file work..
				// rewrite

				// delete old photo
				File deleteFile = new ClassPathResource("/static/img").getFile();
				File file1 = new File(deleteFile, oldModuleDetail.getImage());
				file1.delete();

				// update new photo
				File saveFile = new ClassPathResource("/static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				module.setImage(file.getOriginalFilename());
			} else {
				module.setImage(oldModuleDetail.getImage());

			}
			User user = this.userRepository.getUserByUserName(principal.getName());

			module.setUser(user);

			this.moduleRepository.save(module);

			session.setAttribute("message", new Message("Your Module is updated", "success"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Module NAME" + module.getName());
		System.out.println("Module ID" + module.getcId());
		return "redirect:/user/" + module.getcId() + "/module/";
	}

	// your profile handler

	@GetMapping("/profile")
	public String yourProfile(Model model) {
		model.addAttribute("title", "Profile Page");
		return "normal/profile";
	}

	@GetMapping("/settings")
	public String openSettings() {
		return "normal/settings";
	}

	// change password...handler
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {
		System.out.println("OLDPASSWORD " + oldPassword);
		System.out.println("NEWPASSWORD " + newPassword);

		String userName = principal.getName();
		User currentUser = this.userRepository.getUserByUserName(userName);
		System.out.println(currentUser.getPassword());

		if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
			// change the password
			currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepository.save(currentUser);
			session.setAttribute("message", new Message("Your password is changed successfully", "success"));
		} else {
			// error...
			session.setAttribute("message", new Message("Please Enter correct old password", "danger"));
			return "redirect:/user/settings";
		}

		return "redirect:/user/index";
	}

	// creating order for payment
	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder(@RequestBody Map<String, Object> data, Principal principal) throws RazorpayException {
		// System.out.println("Hey order is executed");
		System.out.println(data);

		int amt = Integer.parseInt(data.get("amount").toString());

		RazorpayClient client = new RazorpayClient("rzp_test_4FCWvdLC7kmLtV", "QqhgaDc6HKUUxUQLd4jW2plJ");

		JSONObject ob = new JSONObject();
		ob.put("amount", amt * 100);
		ob.put("currency", "INR");
		ob.put("receipt", "txn_235425");

		// creating new order
		Order order = client.Orders.create(ob);
		System.out.println(order);

		// save the order in database

		MyOrder myOrder = new MyOrder();

		myOrder.setAmount(order.get("amount") + "");
		myOrder.setOrderId(order.get("id"));
		myOrder.setPaymentId(null);
		myOrder.setStatus("created");
		myOrder.setUser(this.userRepository.getUserByUserName(principal.getName()));
		myOrder.setReceipt(order.get("receipt"));

		this.myOrderRepository.save(myOrder);

		// if you want you can save this to your data...
		return order.toString();
	}

	@PostMapping("/update_order")
	public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data) {

		MyOrder myOrder = this.myOrderRepository.findByOrderId(data.get("order_id").toString());

		myOrder.setPaymentId(data.get("payment_id").toString());
		myOrder.setStatus(data.get("status").toString());

		this.myOrderRepository.save(myOrder);

		System.out.println(data);

		return ResponseEntity.ok(Map.of("msg", "updated"));
	}
}
