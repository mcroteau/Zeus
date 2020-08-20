package xyz.ioc.dao;

import java.util.List;
import java.util.Set;

import xyz.ioc.model.*;

public interface AccountDao {

	public long id();

	public long count();
	
	public Account get(long id);
	
	public Account findByUsername(String username);
	
	public List<Account> findAll();
	
	public List<Account> findAllOffset(int max, int offset);

	public Account save(Account account);

	public Account saveAdministrator(Account account);

	public boolean update(Account account);

	public boolean updateUuid(Account account);

	public boolean updatePassword(Account account);

	public Account findByUsernameAndUuid(String username, String uuid);
	
	public boolean delete(long id);
	
	public String getAccountPassword(String username);

	public boolean saveAccountRole(long accountId, long roleId);
	
	public boolean saveAccountPermission(long accountId, String permission);
	
	public boolean deleteAccountRoles(long accountId);
	
	public boolean deleteAccountPermissions(long accountId);

	public Set<String> getAccountRoles(long id);
	
	public Set<String> getAccountRoles(String username);

	public Set<String> getAccountPermissions(long id);

	public Set<String> getAccountPermissions(String username);

	public long countQuery(String query);

	public List<Account> search(String query, int offset);

	public boolean incrementViews(ProfileView profileView);

	public long getViews(Account account, long start, long end);

	public long getAllViews(Account account);

	public List<ProfileView> getViewsList(Account account, long start, long end);

	public long getAllViewsAll();

	public long likes(long id);

	public boolean like(ProfileLike profileLike);

	public boolean liked(ProfileLike profileLike);

	public boolean unlike(ProfileLike profileLike);

	public boolean updateDisabled(Account account);

	public boolean suspend(Account account);

	public boolean renew(Account account);

	public boolean block(AccountBlock accountBlock);

	public boolean blocked(AccountBlock accountBlock);

	public boolean unblock(AccountBlock accountBlock);

}