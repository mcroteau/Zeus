package xyz.ioc.accessor;

import io.github.mcroteau.resources.access.Accessor;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.ioc.dao.AccountDao;
import xyz.ioc.model.Account;

import java.util.Set;

public class ZeusAccessor implements Accessor {

    @Autowired
    private AccountDao accountDao;

    public String getPassword(String user){
        String password = accountDao.getAccountPassword(user);
        System.out.println("pass: " + password);
        return password;
    }

    public Set<String> getRoles(String user){
        Account account = accountDao.findByUsername(user);
        Set<String> roles = accountDao.getAccountRoles(account.getId());
        return roles;
    }

    public Set<String> getPermissions(String user){
        Account account = accountDao.findByUsername(user);
        Set<String> permissions = accountDao.getAccountPermissions(account.getId());
        return permissions;
    }

}
