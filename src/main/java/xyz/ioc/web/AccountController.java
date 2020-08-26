package xyz.ioc.web;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import io.github.mcroteau.Parakeet;
import xyz.ioc.common.Constants;
import xyz.ioc.dao.*;
import xyz.ioc.model.*;
import xyz.ioc.common.Utilities;
import xyz.ioc.service.EmailService;
import xyz.ioc.service.PhoneService;


@Controller
public class AccountController extends BaseController {

	private static final Logger log = Logger.getLogger(AccountController.class);

	Gson gson = new Gson();


	@Autowired
	private Parakeet parakeet;

	@Autowired
	private Utilities utilities;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private FriendDao friendDao;

	@Autowired
	private MusicDao musicDao;

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private NotificationDao notificationDao;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PhoneService phoneService;


    @RequestMapping(value="/account/info", method=RequestMethod.GET)
    public @ResponseBody String info(ModelMap model, HttpServletRequest request){

        if(!authenticated()){
            Map<String, String> data = new HashMap<String, String>();
            data.put("error", "Not authenticated");
            return gson.toJson(data);
        }

        Account account = getAuthenticatedAccount();
        return gson.toJson(account);
    }


	@RequiresAuthentication
	@RequestMapping(value="/accounts", method=RequestMethod.GET)
	public String accounts(ModelMap model, 
					final RedirectAttributes redirect, 
				    @RequestParam(value="admin", required = false ) String admin,
				    @RequestParam(value="offset", required = false ) String offset,
				    @RequestParam(value="max", required = false ) String max,
				    @RequestParam(value="page", required = false ) String page){

		if(!administrator()){
			redirect.addFlashAttribute("error", "You are not administrator.");
			return "redirect:/";
		}

		if(page == null){
			page = "1";
		}						

		List<Account> accounts = new ArrayList<Account>();
		
		if(offset != null) {
			int m = Constants.RESULTS_PER_PAGE;
			if(max != null){
				m = Integer.parseInt(max);
			}
			int o = Integer.parseInt(offset);
			accounts = accountDao.findAllOffset(m, o);	
		}else{
			accounts = accountDao.findAll();	
		} 
		
		long count = accountDao.count();
		
		model.addAttribute("accounts", accounts);
		model.addAttribute("total", count);
		
		model.addAttribute("resultsPerPage", Constants.RESULTS_PER_PAGE);
		model.addAttribute("activePage", page);

		model.addAttribute("accountsHrefActive", "active");
		
    	return "account/index";

	}




	@RequestMapping(value="/account/edit/{id}", method=RequestMethod.GET)
	public String edit(ModelMap model, 
	                     HttpServletRequest request,
						 final RedirectAttributes redirect,
					     @PathVariable String id){

		if(administrator() || 
				hasPermission(Constants.ACCOUNT_MAINTENANCE + id)){

			Account account = accountDao.get(Long.parseLong(id));
			model.addAttribute("account", account);

			return "account/edit";

		}else{
			redirect.addFlashAttribute("error", "You do not have permission to edit this account.");
			return "redirect:/";
		}
		
	}


	@RequestMapping(value="/account/update/{id}", method=RequestMethod.GET)
	public String uget(ModelMap model,
					  	 	 final RedirectAttributes redirect,
					     	@PathVariable String id){


		if(administrator() ||
				hasPermission("account:maintenance:" + id)){


			Account account = accountDao.get(Long.parseLong(id));

			model.addAttribute("account", account);
			return "account/edit";

		}else{
			redirect.addFlashAttribute("error", "You don't hava permissionsa...");
			return "redirect:/";
		}

	}


	@RequestMapping(value="/account/update/{id}", method=RequestMethod.POST)
	public String update(@ModelAttribute("account")
							 Account account, 
							 ModelMap model,
					   		 HttpServletRequest request,
					  	 	 final RedirectAttributes redirect, 
					  	 	 @RequestParam(value="image", required=false) CommonsMultipartFile uploadedProfileImage){
		
		long id = account.getId();
		Account storedAccount = accountDao.get(id);

		String imageFileUri = "";

		if(administrator() || 
				hasPermission(Constants.ACCOUNT_MAINTENANCE + id)){


			if(uploadedProfileImage != null &&
					uploadedProfileImage.getSize() > 0) {
				imageFileUri = utilities.write(uploadedProfileImage, Constants.PROFILE_IMAGE_DIRECTORY);
				if(imageFileUri.equals("")){
					utilities.deleteUploadedFile(imageFileUri);
					redirect.addFlashAttribute("account", account);
					redirect.addFlashAttribute("error", "Something went wrong while processing image. PNG, JPG or GIF only.");
					return "redirect:/account/edit/" + id;
				}
				account.setImageUri(imageFileUri);

				if(!storedAccount.getImageUri().equals(Constants.DEFAULT_IMAGE_URI) &&
						!storedAccount.getImageUri().equals(Constants.FRESCO)) {
					System.out.println(">>> deleting existing profile : " + storedAccount.getImageUri());
					utilities.deleteUploadedFile(storedAccount.getImageUri());
				}
			}

			if(!account.getImageUri().equals("")) {
				accountDao.update(account);
				Account savedAccount = accountDao.get(id);

				//TODO: update session account

				redirect.addFlashAttribute("message", "account successfully updated");
				model.addAttribute("account", savedAccount);

				return "redirect:/account/edit/" + id;
			}
			else{
				redirect.addFlashAttribute("account", account);
				redirect.addFlashAttribute("error", "Please include your profile image");
				return "redirect:/account/edit/" + id;
			}

		}else{
			redirect.addFlashAttribute("error", "You don't hava permissionsa...");
			return "redirect:/";
		}

	}


	@RequestMapping(value="/account/edit_password/{id}", method=RequestMethod.GET)
	public String editPassword(ModelMap model, 
	                     HttpServletRequest request,
						 final RedirectAttributes redirect,
					     @PathVariable String id){

		if(administrator() || 
				hasPermission("account:maintenance:" + id)){

			Account account = accountDao.get(Long.parseLong(id));
			model.addAttribute("account", account);
			return "account/edit_password";

		}else {
			redirect.addFlashAttribute("error", "You do not have permission to edit this account. What are you up to?");
			return "redirect:/";
		}
	}


	@RequestMapping(value="/account/update_password/{id}", method=RequestMethod.POST)
	public String updatePassword(@ModelAttribute("account")
							 Account account, 
							 ModelMap model,
					   		 HttpServletRequest request,
					  	 	 final RedirectAttributes redirect // @RequestParam("image") CommonsMultipartFile file
			   				 ){
		
		if(account.getPassword().length() < 7){
		 	redirect.addFlashAttribute("account", account);
			redirect.addFlashAttribute("error", "Passwords must be at least 7 characters long.");
			return "redirect:/signup";
		}

		if(administrator() || 
				hasPermission("account:maintenance:" + account.getId())){
			
			if(!account.getPassword().equals("")){
				String password = utilities.hash(account.getPassword());
				account.setPassword(password);
				accountDao.updatePassword(account);
			}

			redirect.addFlashAttribute("message", "password successfully updated");	
			return "redirect:/signout";
			
		}else{
			redirect.addFlashAttribute("error", "You don't hava permissionsa...");
			return "redirect:/";
		}
	}

	@RequestMapping(value="/account/delete/{id}", method=RequestMethod.POST)
	public String deleteAccount(ModelMap model,
								  HttpServletRequest request,
								  final RedirectAttributes redirect,
								  @PathVariable String id) {

		if(!administrator()){
			redirect.addFlashAttribute("error", "You don't hava permissionsa...");
			return "redirect:/accounts";
		}

		Account account = accountDao.get(Long.parseLong(id));
		List<Post> posts = postDao.fetchUserPosts(Long.parseLong(id));
		for(Post post : posts){
			postDao.hide(post.getId());
			postDao.removePostShares(post.getId());
		}

		account.setDisabled(true);
		account.setDateDisabled(utilities.getCurrentDate());
		accountDao.suspend(account);

		redirect.addFlashAttribute("message", "Successfully disabled account");

		return "redirect:/accounts";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(HttpServletRequest request, ModelMap model, @RequestParam(value="uri", required = false ) String uri, @ModelAttribute("account") Account account){
		parakeet.logout();
    	model.addAttribute("uri", uri);
		return "account/signup";
	}
	

	@RequestMapping(value="/register", method=RequestMethod.POST)
	protected String register(HttpServletRequest req,
							@ModelAttribute("account") Account account,
							  @RequestParam(value="uri", required = false ) String uri,
							  RedirectAttributes redirect){


		if(!utilities.validEmail(account.getUsername())){
			redirect.addFlashAttribute("account", account);
			redirect.addFlashAttribute("error", "Username must be a valid email.");
			return "redirect:/signup?uri=" + uri;
		}

		if(account.getUsername().contains(" ")){
			redirect.addFlashAttribute("account", account);
			redirect.addFlashAttribute("error", "Username contains spaces, no spaces are allowed");
			return "redirect:/signup?uri=" + uri;
		}

		if(account.getName().equals("")){
			redirect.addFlashAttribute("account", account);
			redirect.addFlashAttribute("error", "Name cannot be blank.");
			return "redirect:/signup?uri=" + uri;
		}
		
		if(account.getPassword().equals("")) {
			redirect.addFlashAttribute("account", account);
			redirect.addFlashAttribute("error", "Password cannot be blank");
			return "redirect:/signup?uri=" + uri;
		}

		if(account.getPassword().length() < 7){
			redirect.addFlashAttribute("account", account);
			redirect.addFlashAttribute("error", "Password must be at least 7 characters long.");
			return "redirect:/signup?uri=" + uri;
		}

		String password = account.getPassword();
		String passwordHashed = utilities.hash(account.getPassword());

        try{

			account.setPassword(passwordHashed.toString());
			account.setImageUri(Constants.DEFAULT_IMAGE_URI);
			accountDao.save(account);	
			
			Account savedAccount = accountDao.findByUsername(account.getUsername());
			preloadConnections(savedAccount);

			Role defaultRole = roleDao.find(Constants.ROLE_ACCOUNT);

			accountDao.saveAccountRole(savedAccount.getId(), defaultRole.getId());
			accountDao.saveAccountPermission(savedAccount.getId(), "account:maintenance:" + savedAccount.getId());

			String body = "<h1>Zeus</h1>"+
					"<p>Thank you for registering! Enjoy!</p>";

			emailService.send(savedAccount.getUsername(), "Successfully Registered", body);
			phoneService.support("Zeus : Registration " + account.getName() + " " + account.getUsername());

        }catch(Exception e){
			e.printStackTrace();
			redirect.addFlashAttribute("account", account);
        	redirect.addFlashAttribute("error", "Will you contact us? Email us with the subject, Please Fix. support@gosigma.co. Our programmers missed something. Gracias");
        	return("redirect:/signup?uri=" + uri);
        }


        if(parakeet.login(account.getUsername(), password)) {

			req.getSession().setAttribute("account", account);
			req.getSession().setAttribute("imageUri", account.getImageUri());

			return "redirect:/?uri=" + uri;

		}else{
			redirect.addFlashAttribute("message", "Thank you for registering. Enjoy");
			return "redirect:/?uri=" + uri;
		}
	}

	

	@RequestMapping(value="/register_mobile", method=RequestMethod.POST, consumes="application/json")
	protected @ResponseBody String registerMobile(
							 @RequestBody Account account,
							  @RequestParam(value="name", required = false ) String name,
							  @RequestParam(value="email", required = false ) String email,
							  @RequestParam(value="password", required = false ) String password,
							   HttpServletRequest request){


		Map<String, String> data = new HashMap<String, String>();
		
		try{

			if(account == null){
				String payload = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
		    	account = gson.fromJson(payload, Account.class);
			}

		}catch(Exception e){
			data.put("error", "IO error");
			return gson.toJson(data);
		}

		if(!utilities.validEmail(account.getUsername())){
			data.put("error", "Username must be a valid email");
		}

		if(account.getUsername().contains(" ")){
			data.put("error", "Username contains spaces, no spaces are allowed");
		}

		if(account.getName().equals("")){
			data.put("error", "Name cannot be blank.");
		}
		
		if(account.getPassword().equals("")) {
			data.put("error", "Password cannot be blank.");
		}

		if(account.getPassword().length() < 7){
			data.put("error", "Passwords must be at least 7 characters long.");
		}

		String passwordHashed = utilities.hash(account.getPassword());

        try{

			account.setPassword(passwordHashed.toString());
			account.setImageUri(Constants.DEFAULT_IMAGE_URI);
			accountDao.save(account);	
			
			Account savedAccount = accountDao.findByUsername(account.getUsername());
			preloadConnections(savedAccount);

			Role defaultRole = roleDao.find(Constants.ROLE_ACCOUNT);

			accountDao.saveAccountRole(savedAccount.getId(), defaultRole.getId());
			accountDao.saveAccountPermission(savedAccount.getId(), "account:maintenance:" + savedAccount.getId());

			String body = "<h1>Zeus</h1>"+
					"<p>Thank you for registering! Enjoy!</p>";

			emailService.send(savedAccount.getUsername(), "Successfully Registered", body);
			phoneService.support("Zeus : Registration " + account.getName() + " " + account.getUsername());

        }catch(Exception e){
			e.printStackTrace();
			data.put("error",  "Will you contact us? Email us with the subject, Please Fix. support@gosigma.co. Our programmers missed something. Gracias");
        }

		data.put("success", "true");
		return gson.toJson(data);
	}



	@RequestMapping(value="/profile/{id}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String profile(ModelMap model,
						  final RedirectAttributes redirect,
						  @PathVariable String id){

		Map<String, Object> data = new HashMap<String, Object>();

		if(!authenticated()){
			data.put("error", "Authentication required");
			return gson.toJson(data);
		}

		Account account = accountDao.get(Long.parseLong(id));
		Account authenticatedAccount = getAuthenticatedAccount();

		if(account.getId() == authenticatedAccount.getId()){
			account.setOwnersAccount(true);
		}

		boolean ifFriend = friendDao.isFriend(authenticatedAccount.getId(), account.getId());
		if(ifFriend){
			account.setIsFriend(true);
		}

		AccountBlock blok = new AccountBlock.Builder()
				.forPerson(Long.parseLong(id))
				.byBlocker(authenticatedAccount.getId())
				.build();

		if(accountDao.blocked(blok)){
			account.setBlocked(true);
		}

		List<Friend> friends = friendDao.getFriends(Long.parseLong(id));
        for(Friend friend : friends){
            if(messageDao.hasMessages(friend.getFriendId(), getAuthenticatedAccount().getId()))
                friend.setHasMessages(true);
        }

		long likes = accountDao.likes(account.getId());
        if(likes > 0){
        	account.setLiked(true);
		}
		account.setLikes(likes);

        data.put("profile", account);
        data.put("friends", friends);

        ProfileView view = new ProfileView.Builder()
				.profile(account.getId())
				.viewer(authenticatedAccount.getId())
				.date(utilities.getCurrentDate())
				.build();

        accountDao.incrementViews(view);

		return gson.toJson(data);
	}


	@RequestMapping(value="/account/reset", method=RequestMethod.GET)
	public String reset(){
		return "account/reset";
	}

	@RequestMapping(value="/account/send_reset", method=RequestMethod.POST)
	public String sendReset(HttpServletRequest request,
							final RedirectAttributes redirect,
				    			@RequestParam(value="username", required = true ) String username){


		try {
			Account account = accountDao.findByUsername(username);

			if (account == null) {
				redirect.addFlashAttribute("error", "Unable to find account.");
				return ("redirect:/account/reset");
			}

			String resetUuid = utilities.generateRandomString(13);
			account.setUuid(resetUuid);
			accountDao.updateUuid(account);

			StringBuffer url = request.getRequestURL();

			String[] split = url.toString().split("/b/");
			String httpSection = split[0];

			String resetUrl = httpSection + "/b/account/confirm_reset?";

			String params = "username=" + URLEncoder.encode(account.getUsername(), "utf-8") + "&uuid=" + resetUuid;
			resetUrl += params;


			String body = "<h1>Zeus</h1>" +
					"<p>Reset Password :" +
					"<a href=\"" + resetUrl + "\">" + resetUrl + "</a></p>";

			emailService.send(account.getUsername(), "Reset Password", body);

		}catch(Exception e){
			e.printStackTrace();
		}

		return "account/send_reset";
	}


	@RequestMapping(value="/account/confirm_reset", method=RequestMethod.GET)
	public String reset(ModelMap model,
						final RedirectAttributes redirect,
						@RequestParam(value="username", required = true ) String username,
						@RequestParam(value="uuid", required = true ) String uuid){

		Account account = accountDao.findByUsernameAndUuid(username, uuid);

		if (account == null) {
			redirect.addFlashAttribute("error", "Unable to find account.");
			return ("redirect:/account/reset");
		}
		model.addAttribute("account", account);

		return "account/confirm";
	}



	@RequestMapping(value="/account/reset/{id}", method=RequestMethod.POST)
	public String resetPassword(@ModelAttribute("account") Account account,
								 ModelMap model,
								 HttpServletRequest request,
								 final RedirectAttributes redirect){

		if(account.getPassword().length() < 7){
			redirect.addFlashAttribute("account", account);
			redirect.addFlashAttribute("error", "Passwords must be at least 7 characters long.");
			return "redirect:/account/confirm?username=" + account.getUsername() + "&uuid=" + account.getUuid();
		}

		if(!account.getPassword().equals("")){
			String password = utilities.hash(account.getPassword());
			account.setPassword(password);
			accountDao.updatePassword(account);
		}

		redirect.addFlashAttribute("message", "Password successfully updated");
		return "account/success";

	}


	@RequestMapping(value="/account/guest", method=RequestMethod.GET)
	public String guest(HttpServletRequest request){

		try {

			if(parakeet.login(Constants.GUEST_USERNAME, Constants.GUEST_PASSWORD)) {
				Account sessionAccount = getAuthenticatedAccount();
//				parakeet.store("account", sessionAccount);
//				parakeet.store("imageUri", sessionAccount.getImageUri());

				request.getSession(false).setAttribute("account", sessionAccount);
				request.getSession(false).setAttribute("imageUri", sessionAccount.getImageUri());
			}
			//phoneService.support("Zq:" + request.getRemoteHost());

		}catch(Exception e){ }

		return "redirect:/";
	}


	private void preloadConnections(Account authenticatedAccount){
		Account adminAccount = accountDao.findByUsername(Constants.ADMIN_USERNAME);
		friendDao.saveConnection(adminAccount.getId(), authenticatedAccount.getId(), utilities.getCurrentDate());
	}


	@RequestMapping(value="/profile/like/{id}", method=RequestMethod.POST,  produces="application/json")
	public @ResponseBody String like(ModelMap model,
									 HttpServletRequest request,
									 final RedirectAttributes redirect,
									 @PathVariable String id){

		Gson gson = new Gson();
		Map<String, Object> responseData = new HashMap<String, Object>();

		if(!authenticated()){
			responseData.put("error", "authentication required");
			return gson.toJson(responseData);
		}

		Account account = getAuthenticatedAccount();

		ProfileLike profileLike = new ProfileLike();
		profileLike.setLikerId(account.getId());
		profileLike.setProfileId(Long.parseLong(id));
		profileLike.setDateLiked(utilities.getCurrentDate());

		boolean result = false;

		boolean existingProfileLike = accountDao.liked(profileLike);

		if(existingProfileLike) {
			responseData.put("action", "removed");
			result = accountDao.unlike(profileLike);
		}
		else{
			result = accountDao.like(profileLike);
			responseData.put("action", "added");
		}

		long likes = accountDao.likes(Long.parseLong(id));


		responseData.put("success", result);
		responseData.put("likes", likes);
		responseData.put("id", Long.parseLong(id));

		return gson.toJson(responseData);
	}



	@RequestMapping(value="/profile/block/{id}", method=RequestMethod.POST,  produces="application/json")
	public @ResponseBody String blockUser(ModelMap model,
									 HttpServletRequest request,
									 final RedirectAttributes redirect,
									 @PathVariable String id) {
		Gson gson = new Gson();
		Map<String, Object> resp = new HashMap<String, Object>();

		if(!authenticated()){
			resp.put("error", "authentication required");
			return gson.toJson(resp);
		}

		Account account = getAuthenticatedAccount();

		if(account.getId() == Long.parseLong(id)){
			resp.put("error", "cannot block yourself b");
			return gson.toJson(resp);
		}


		AccountBlock blok = new AccountBlock.Builder()
				.forPerson(Long.parseLong(id))
				.byBlocker(account.getId())
				.atDateBlocked(utilities.getCurrentDate())
				.build();

		log.info("b: " + blok.toString());

		if(accountDao.blocked(blok)){
			resp.put("success", "unblocked");
			accountDao.unblock(blok);
		}else{
			resp.put("success", "blocked");
			accountDao.block(blok);
		}

		return gson.toJson(resp);
	}


	@RequestMapping(value="/profile/data", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String data(ModelMap model,
									 HttpServletRequest request,
									 final RedirectAttributes redirect){

    	Map<String, Object> data = new HashMap<String, Object>();

		if(!authenticated()){
			System.out.println("not authenticated");
			data.put("error", "Authentication required");
			return gson.toJson(data);
		}

		Account account = getAuthenticatedAccount();

		List<Post> latestPostsTiny = getLatestPostsSkinny(account, request);
		long messagesCount = messageDao.countByAccount(account);
		long notificationsCount = notificationDao.countByAccount(account);
		long invitationsCount = friendDao.countInvitesByAccount(account);

		data.put("latestPosts", latestPostsTiny);
		data.put("messagesCount", messagesCount);
		data.put("notificationsCount", notificationsCount);
		data.put("invitationsCount", invitationsCount);

    	return gson.toJson(data);
	}


	private List<Post> getLatestPostsSkinny(Account account, HttpServletRequest request){
		List<Post> latest = new ArrayList<Post>();

		try {

			if (request.getSession().getAttribute("feed-request-time") != null) {
				long start = (Long) request.getSession().getAttribute("feed-request-time");
				long end = utilities.getCurrentDate();
				latest = postDao.getLatestPostsSkinny(start, end, account.getId());
			}

		}catch(Exception e){
			// log.error(e.getMessage());
		}

		return latest;
	}



	@RequestMapping(value="/profile/data/views", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String views(ModelMap model,
										final RedirectAttributes redirect){
		Map<String, Object> viewsData = new HashMap<String, Object>();

		if(!authenticated()){
			viewsData.put("error", "Authentication required");
			return gson.toJson(viewsData);
		}

		Account account = getAuthenticatedAccount();

		long end = utilities.getCurrentDate();

		List<String> labels = new ArrayList<String>();
		List<Long> counts = new ArrayList<Long>();
		;
		int day = 31;
		long currentDate = utilities.getPreviousDay(day);

		for(int n = 32; n != 0; n--){
			long date = utilities.getPreviousDay(n);
			long count = accountDao.getViews(account, date, currentDate);

			String datef = utilities.getGraphDate(n);
			labels.add(datef);
			counts.add(count);

			day--;
			currentDate = utilities.getPreviousDay(day);
		}


		long weekCount = accountDao.getViews(account, utilities.getPreviousDay(7), end);
		long monthCount = accountDao.getViews(account, utilities.getPreviousDay(31), end);
		long allTimeCount = accountDao.getAllViews(account);

		viewsData.put("labels", labels);
		viewsData.put("counts", counts);
		viewsData.put("week", weekCount);
		viewsData.put("month", monthCount);
		viewsData.put("all", allTimeCount);

		return gson.toJson(viewsData);
	}


	@RequestMapping(value="/account/suspend/{id}", method=RequestMethod.POST)
	public String suspend(ModelMap model,
					   final RedirectAttributes redirect,
					   @PathVariable String id){
		if(!administrator()){
			redirect.addFlashAttribute("message", "You don't have permission to do this!");
			return "redirect:/account/profile/" + id;
		}

		Account account = accountDao.get(Long.parseLong(id));
		account.setDateDisabled(utilities.getCurrentDate());
		accountDao.suspend(account);

		model.addAttribute("message", "Account suspended.");
		return "redirect:/account/edit/" + id;
    }


	@RequestMapping(value="/account/renew/{id}", method=RequestMethod.POST)
	public String renew(ModelMap model,
						  final RedirectAttributes redirect,
						  @PathVariable String id){

		if(!administrator()){
			redirect.addFlashAttribute("message", "You don't have permission to do this!");
			return "redirect:/account/profile/" + id;
		}

		Account account = accountDao.get(Long.parseLong(id));
		accountDao.renew(account);

		model.addAttribute("message", "Account renewed.");
		return "redirect:/account/edit/" + id;
	}
}