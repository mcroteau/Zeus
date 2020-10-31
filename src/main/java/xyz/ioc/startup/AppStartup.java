package xyz.ioc.startup;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import xyz.ioc.common.Constants;
import xyz.ioc.common.Utilities;
import xyz.ioc.dao.*;
import xyz.ioc.model.*;


public class AppStartup implements ApplicationListener<ContextRefreshedEvent>{

	private static final Logger log = Logger.getLogger(AppStartup.class);

	@Autowired
	public RoleDao roleDao;

	@Autowired
	public AccountDao accountDao;

	@Autowired
	public PostDao postDao;
	
	@Autowired
	public FriendDao friendDao;

	@Autowired
	public MessageDao messageDao;

	@Autowired
	public Utilities utilities;

	public void onApplicationEvent(ContextRefreshedEvent contextRefreshEvent) {
		createApplicationRoles();
		createApplicationAdministrator();
		createApplicationGuest();
	}

	private void createApplicationRoles(){
		Role adminRole = roleDao.find(Constants.ROLE_ADMIN);
		Role accountRole = roleDao.find(Constants.ROLE_ACCOUNT);

		if(adminRole == null){
			adminRole = new Role();
			adminRole.setName(Constants.ROLE_ADMIN);
			roleDao.save(adminRole);
		}

		if(accountRole == null){
			accountRole = new Role();
			accountRole.setName(Constants.ROLE_ACCOUNT);
			roleDao.save(accountRole);
		}

		log.info("Roles : " + roleDao.count());
	}

	
	private void createApplicationAdministrator(){
		
		try{
			Account existing = accountDao.findByUsername(Constants.ADMIN_USERNAME);
			String password = io.github.mcroteau.resources.Constants.hash(Constants.PASSWORD);

			if(existing == null){
				Account admin = new Account();
				admin.setName("Adminstrator");
				admin.setUsername(Constants.ADMIN_USERNAME);
				admin.setPassword(password);
				admin.setImageUri(Constants.FRESCO);
				accountDao.saveAdministrator(admin);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info("Accounts : " + accountDao.count());
	}



	private void createApplicationGuest(){
		Account existing = accountDao.findByUsername(Constants.GUEST_USERNAME);
		String password = utilities.hash(Constants.GUEST_PASSWORD);

		if(existing == null){
			Account account = new Account();
			account.setName("Kelly");
			account.setUsername(Constants.GUEST_USERNAME);
			account.setPassword(password);
			account.setImageUri(Constants.DEFAULT_IMAGE_URI);
			accountDao.save(account);
		}
		log.info("Accounts : " + accountDao.count());
	}



	private void generateAppData(){
		generateMockAccounts();
		//generateMockFriendInvites();
		generateMockConnections();
		generateMockPosts();
		//generateMockMessages();
		generateMockViewData();
	}


	private void generateMockAccounts(){
		long count = accountDao.count();
		if(count == 2){
			for(int m = 0; m < Constants.MOCK_ACCOUNTS; m++){
				Account account = new Account();
				String name = "Prometheus " + utilities.generateRandomString(9);
				account.setName(name);
				account.setUsername("croteau.mike+"+ m + "@gmail.com");
				account.setLocation(utilities.generateRandomString(7));
				account.setAge("33");
				String password = utilities.hash(Constants.PASSWORD);
				account.setPassword(password);
				account.setImageUri(Constants.FRESCO);
				Account savedAccount = accountDao.save(account);
				accountDao.saveAccountPermission(savedAccount.getId(), Constants.ACCOUNT_MAINTENANCE + savedAccount.getId());
			}
		}

		log.info("Accounts : " + accountDao.count());
	}




	private void generateMockFriendInvites(){
		try{

			List<Account> accounts = accountDao.findAll();

			int possibilities = accounts.size() * accounts.size();
			long currentDate = utilities.getCurrentDate();

			for (int n = 0; n < possibilities; n++) {
				for (Account account : accounts) {
					List<Account> possibleInvites = accountDao.findAll();
					Account invited = possibleInvites.get(n);
					friendDao.invite(account.getId(), invited.getId(), currentDate);
				}
			}

		}catch(Exception e){
			log.info("duplicate invite");
		}

		log.info("Friend Invites : " + friendDao.countInvites());
	}



	private void generateMockConnections() {
		try {
			long count = friendDao.count();

			if (count == 0) {
				List<Account> accounts = accountDao.findAll();

				int possibilities = accounts.size() * accounts.size();
				long currentDate = utilities.getCurrentDate();

				for (int n = 0; n < possibilities; n++) {

					for (Account account : accounts) {

						List<Account> possibleFriends = accountDao.findAll();
						Random r1 = new Random();

						if (n < possibleFriends.size()) {

							Random doOrDontRandom = new Random();
							int r2 = doOrDontRandom.nextInt(21);

							if (r2 % 13 == 0 && r2 != 0) {
								Account friend = possibleFriends.get(n);

								if (account.getId() != friend.getId()) {
									System.out.print(".");
									friendDao.saveConnection(account.getId(), friend.getId(), currentDate);
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			log.error("duplicate friend connection");
		}

		log.info("Connections : " + friendDao.count()/2);
	}

	
	private void generateMockPosts(){
		
		if(postDao.count() == 0) {
			List<Account> accounts = accountDao.findAll();
			
			for(Account account : accounts){
				Random r1 = new Random();
				int count = r1.nextInt(21);
				
				for(int m = 0; m < count; m++){

					Post post = new Post();
					post.setAccountId(account.getId());

					StringBuilder sb = new StringBuilder();
					for(int n = 0; n < 7; n++){
						sb.append("The Lazy Fox jumped over the yellow dog and laughed. ");
					}
					post.setContent(sb.toString());

					long datePosted = utilities.getCurrentDate();
					
					post.setDatePosted(datePosted);
					Post savedPost = postDao.save(post);
					accountDao.saveAccountPermission(account.getId(), Constants.POST_MAINTENANCE + savedPost.getId());

				}
			}
		}
		
		log.info("Posts : " + postDao.count());
	}


	private void generateMockMessages(){
		try{

			List<Account> accounts = accountDao.findAll();
			int possibilities = accounts.size() * accounts.size();

			for (int n = 0; n < possibilities; n++) {
				for (Account account : accounts) {
					List<Account> possibleRecipients = accountDao.findAll();
					Account recipient = possibleRecipients.get(n);
					if(recipient.getId() != account.getId()) {
						Message message = new Message();
						message.setSenderId(account.getId());
						message.setRecipientId(recipient.getId());
						message.setContent(utilities.generateRandomString(15));
						message.setDateSent(utilities.getCurrentDate());
						message.setViewed(false);
						messageDao.send(message);
					}
				}
			}

		}catch(Exception e){
			// log.error("message error");
		}

		log.info("Messages : " + messageDao.count());
	}

	private void generateMockViewData() {

		if(accountDao.getAllViewsAll() == 0) {

			List<Account> accounts = accountDao.findAll();
			for (Account account : accounts) {
				for (int n = 0; n < 61; n++) {

					long time = utilities.getPreviousDay(n);
					int views = utilities.generateRandomNumber(170);

					for (int m = 0; m < views; m++) {
						ProfileView view = new ProfileView.Builder()
								.profile(account.getId())
								.viewer(account.getId())
								.date(time)
								.build();
						accountDao.incrementViews(view);
					}
				}
			}
		}

		log.info("Profile Views : " + accountDao.getAllViewsAll());
	}

}