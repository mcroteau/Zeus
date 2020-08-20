package xyz.ioc.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.mcroteau.Parakeet;
import xyz.ioc.common.Constants;
import xyz.ioc.dao.AccountDao;
import xyz.ioc.model.Account;


@Controller
public class BaseController {

	private static final Logger log = Logger.getLogger(BaseController.class);

	@Autowired
	private Parakeet parakeet;

	@Autowired
	public AccountDao accountDao;


	public boolean administrator(){
		return parakeet.hasRole(Constants.ROLE_ADMIN);
	}	

	public boolean authenticated(){
		return parakeet.isAuthenticated();
	}

	public boolean hasPermission(String str){
		return parakeet.hasPermission(str);
	}

	public Account getAuthenticatedAccount(){
		String user = parakeet.getUser();
		Account account = accountDao.findByUsername(user);
		return account;
	}
	
}