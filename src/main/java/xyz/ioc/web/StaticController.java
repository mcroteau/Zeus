package xyz.ioc.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.ioc.common.Utilities;
import xyz.ioc.model.Account;
import xyz.ioc.service.EmailService;
import xyz.ioc.service.PhoneService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class StaticController extends BaseController {

	private static final Logger log = Logger.getLogger(StaticController.class);

	@Autowired
	private PhoneService phoneService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private Utilities utilities;


	@RequestMapping(value="/", method=RequestMethod.GET)
	public String portal(Device device,
						 ModelMap model,
						 HttpServletRequest req,
						 HttpServletResponse resp,
						 final RedirectAttributes redirect){

		if(!authenticated()){
			return "redirect:/uno";
		}

		Account account = getAuthenticatedAccount();
		if(account.isDisabled()) {
			return "redirect:/account/edit/" + account.getId();
		}

		req.getSession().removeAttribute("message");

		if(device.isMobile()) {
			return "redirect:/mobile";
		}else{
			return "portal";
		}
	}

	@RequestMapping(value="/mobile", method=RequestMethod.GET)
	public String mobile(Device device, HttpServletRequest req){

		if(!authenticated()){
			return "redirect:/uno";
		}

		Account account = getAuthenticatedAccount();
		if(account.isDisabled()) {
			return "redirect:/account/edit/" + account.getId();
		}

		return "portal_mobile";

	}


	@RequestMapping(value="/uno", method=RequestMethod.GET)
	public String uno(@RequestParam(value="uri", required = false) String uri,
					  HttpServletRequest req){
		if(uri != null &&
			!uri.equals("")){
			req.setAttribute("uri", uri);
		}
		return "uno";
	}


	@RequestMapping(value="/dos", method=RequestMethod.GET)
	public String dos(HttpServletRequest request){
		phoneService.support("Qq:" + request.getRemoteHost());
		return "dos";
	}

	@RequestMapping(value="/eula", method=RequestMethod.GET)
	public String eula(){
		return "static/eula";
	}

	@RequestMapping(value="/privacy", method=RequestMethod.GET)
	public String privacy(){
		return "static/privacy";
	}


	@RequestMapping(value="/get_code", method=RequestMethod.GET)
	public String getCode(){
		return "get_code";
	}


	@RequestMapping(value="/issues/report", method=RequestMethod.GET)
	public String report(){
		phoneService.support("Zeus:issue");
		return "report";
	}


	@RequestMapping(value="/issues/report", method=RequestMethod.POST)
	public String reportIssue(
			@RequestParam(value="email", required = true ) String email,
			@RequestParam(value="issue", required = true ) String issue,
			final RedirectAttributes redirect,
			ModelMap model){


		if (email.equals("")) {
			redirect.addFlashAttribute("error", "Please enter a valid email address");
			return "redirect:/issues/report";
		}

		if (issue.equals("")) {
			redirect.addFlashAttribute("error", "Issue was left black, please tell us what happened.");
			return "redirect:/issues/report";
		}

		StringBuffer sb = new StringBuffer();
		sb.append(email);
		sb.append("<br/>");
		sb.append(issue);
		emailService.send("croteau.mike+zeus@gmail.com", "Zeus", sb.toString());

		model.addAttribute("message", "Thank you. Issue has been reported.");
		return "success";
	}


	@RequestMapping(value="/invite", method=RequestMethod.GET)
	public String invite(){
		return "invite";
	}


	@RequestMapping(value="/invite", method=RequestMethod.POST)
	public String sendInvite(
			@RequestParam(value="emails", required = true ) String emails,
			final RedirectAttributes redirect,
			ModelMap model){

		if(!authenticated()){
			redirect.addFlashAttribute("error", "Please signin to continue...");
			return "redirect:/signin";
		}

		Account account = getAuthenticatedAccount();

		if (emails.equals("")) {
			redirect.addFlashAttribute("error", "Please enter valid email addresses");
			return "redirect:/invite";
		}

		String body = "<h1>Zeus</h1>" +
				"<p>" + account.getName() + " invited you to join Zeus! " +
				"<a href=\"https://zeus.social\">https://zeus.social</a>";

		emailService.send(emails, "You have been invited to join!", body);

		model.addAttribute("message", "Invite(s) have been sent! Thank you!");
		return "success";
	}

}