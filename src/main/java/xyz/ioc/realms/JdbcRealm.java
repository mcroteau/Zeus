package xyz.ioc.realms;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import java.util.Set;

import xyz.ioc.dao.AccountDao;

public class JdbcRealm extends AuthorizingRealm {


	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private CredentialsMatcher credentialsMatcher;
	
	
    protected boolean permissionsLookupEnabled = true;


    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
		
        if (username == null)  {
            System.out.println("\n\napache shiro: no username\n\n");
        	// throw new AccountException("Null usernames are not allowed by this realm.");
        }

        String password = accountDao.getAccountPassword(username);
         
        if (password == null) {
            System.out.println("\n\napache shiro: no password\n\n");
        	// throw new UnknownAccountException("No account found for user [" + username + "]");
        }

        AuthenticationInfo info = buildAuthenticationInfo(username, password.toCharArray());

        return info;
    }


    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        if (principals == null) {
        	throw new AuthorizationException("Principal collection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);
		
        Set<String> roleNames = accountDao.getAccountRoles(username);
		Set<String> permissions = accountDao.getAccountPermissions(username);
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        
        return info;
    }

    
    protected AuthenticationInfo buildAuthenticationInfo(String username, char[] password) {
        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
