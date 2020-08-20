package xyz.ioc.dao.impl.jdbc;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import xyz.ioc.common.Constants;

import xyz.ioc.dao.AccountDao;
import xyz.ioc.dao.RoleDao;

import xyz.ioc.model.*;


public class AccountDaoJdbc implements AccountDao {

	private static final Logger log = Logger.getLogger(AccountDaoJdbc.class);

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	public long id() {
		String sql = "select max(id) from account";
		long id = jdbcTemplate.queryForObject(sql, new Object[]{}, Long.class);
		return id;
	}

	public long count() {
		String sql = "select count(*) from account";
		long count = jdbcTemplate.queryForObject(sql, new Object[] { }, Long.class);
	 	return count; 
	}

	public Account get(long id) {
		String sql = "select * from account where id = ?";
		
		Account account = jdbcTemplate.queryForObject(sql, new Object[] { id }, 
				new BeanPropertyRowMapper<Account>(Account.class));
		
		if(account == null) account = new Account();

		return account;
	}

	public Account findByUsername(String username) {
		Account account = null;
		try{
			String sql = "select * from account where username = ?";
			account = jdbcTemplate.queryForObject(sql, new Object[] { username },
				new BeanPropertyRowMapper<Account>(Account.class));

		}catch(EmptyResultDataAccessException e){
			//TODO:
		}
		return account;	
	}
	
	public List<Account> findAll() {
		String sql = "select * from account";
		List<Account> accounts = jdbcTemplate.query(sql, 
				new BeanPropertyRowMapper<Account>(Account.class));
		return accounts;
	}
	
	public List<Account> findAllOffset(int max, int offset) {
		String sql = "select * from account limit " + max + " offset " + offset;
		List<Account> accounts = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Account>(Account.class));
		return accounts;
	}

	public Account save(Account account) {
		String sql = "insert into account (name, username, age, location, image_uri, password, disabled, date_disabled) values (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] {
				account.getName(), account.getUsername(), account.getAge(), account.getLocation(), account.getImageUri(), account.getPassword(), false, 0
		});

		long id = id();
		Account savedAccount = get(id);

		checkSaveDefaultAccountRole(id);
		checkSaveDefaultAccountPermission(id);

		return savedAccount;
	}

	public Account saveAdministrator(Account account) {
		String sql = "insert into account (name, username, age, location, image_uri, password, disabled, date_disabled) values (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] {
				account.getName(), account.getUsername(), account.getAge(), account.getLocation(), account.getImageUri(), account.getPassword(), false, 0
		});

		long id = id();
		Account savedAccount = get(id);

		checkSaveAdministratorRole(id);
		checkSaveDefaultAccountPermission(id);

		return savedAccount;
	}

	public boolean update(Account account) {
		String sql = "update account set name = ?, age = ?, location = ?, image_uri = ? where id = ?";
		jdbcTemplate.update(sql, new Object[] { 
			account.getName(), account.getAge(), account.getLocation(), account.getImageUri(), account.getId()
		});
		return true;
	}

	public boolean updateUuid(Account account) {
		String sql = "update account set uuid = ? where id = ?";
		jdbcTemplate.update(sql, new Object[] {
				account.getUuid(), account.getId()
		});
		return true;
	}
	
	public boolean updatePassword(Account account) {
		String sql = "update account set password = ? where id = ?";
		jdbcTemplate.update(sql, new Object[] { 
			account.getPassword(), account.getId()     
		});
		return true;
	}

	public Account findByUsernameAndUuid(String username, String uuid){
		Account account = null;
		try{
			String sql = "select * from account where username = '" + username + "' and uuid = '" + uuid + "'";
			account = jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper<Account>(Account.class));

		}catch(EmptyResultDataAccessException e){}

		return account;
	}
	
	public boolean delete(long id) {
		String sql = "delete from account where id = ?";
		jdbcTemplate.update(sql, new Object[] {id });
		return true;
	}

	public String getAccountPassword(String username) {
		Account account = findByUsername(username);
		return account.getPassword();
	}

	public boolean checkSaveAdministratorRole(long accountId){
		Role role = roleDao.find(Constants.ROLE_ADMIN);
		AccountRole existing = getAccountRole(accountId, role.getId());
		if(existing == null){
			saveAccountRole(accountId, role.getId());
		}
		return true;
	}

	public boolean checkSaveDefaultAccountRole(long accountId){
		Role role = roleDao.find(Constants.ROLE_ACCOUNT);
		AccountRole existing = getAccountRole(accountId, role.getId());
		if(existing == null){
			saveAccountRole(accountId, role.getId());
		}
		return true;
	}

	public AccountRole getAccountRole(long accountId, long roleId){
		String sql = "select * from account_roles where account_id = ? and role_id = ?";
		try {
			AccountRole  accountRole = jdbcTemplate.queryForObject(sql, new Object[]{accountId, roleId},
					new BeanPropertyRowMapper<AccountRole>(AccountRole.class));
			return accountRole;
		}catch(Exception e){
			return null;
		}
	}

	public boolean checkSaveDefaultAccountPermission(long accountId){
		String permission = Constants.ACCOUNT_MAINTENANCE + accountId;
		AccountPermission existing = getAccountPermission(accountId, permission);
		if(existing == null){
			saveAccountPermission(accountId, permission);
		}
		return true;
	}

	public AccountPermission getAccountPermission(long accountId, String permission){
		String sql = "select * from account_permissions where account_id = ? and permission = ?";
		try {
			AccountPermission  accountPermission = jdbcTemplate.queryForObject(sql, new Object[]{accountId, permission},
					new BeanPropertyRowMapper<AccountPermission>(AccountPermission.class));
			return accountPermission;
		}catch(Exception e){
			return null;
		}
	}

	public boolean saveAccountRole(long accountId, long roleId){
		String sql = "insert into account_roles (role_id, account_id) values (?, ?)";
		jdbcTemplate.update(sql, new Object[] { 
			roleId, accountId
		});
		return true;
	}
	
	public boolean saveAccountPermission(long accountId, String permission){
		String sql = "insert into account_permissions (account_id, permission) values (?, ?)";
		jdbcTemplate.update(sql, new Object[] { 
			accountId, permission
		});
		return true;
	}
	
	public boolean deleteAccountRoles(long accountId){
		String sql = "delete from account_roles where account_id = ?";
		jdbcTemplate.update(sql, new Object[] { accountId });
		return true;
	}
	
	public boolean deleteAccountPermissions(long accountId){
		String sql = "delete from account_permissions where account_id = ?";
		jdbcTemplate.update(sql, new Object[] { accountId });
		return true;
	}

	public Set<String> getAccountRoles(long id) {	
		String sql = "select r.name from account_roles ur, role r where ur.role_id = r.id and ur.account_id = " + id;
		List<String> rolesList = jdbcTemplate.queryForList(sql, String.class);
		Set<String> roles = new HashSet<String>(rolesList);
		return roles;
	}
	
	public Set<String> getAccountRoles(String username) {	
		Account account = findByUsername(username);
		String sql = "select r.name from account_roles ur, role r where ur.role_id = r.id and ur.account_id = " + account.getId();
		List<String> rolesList = jdbcTemplate.queryForList(sql, String.class);
		Set<String> roles = new HashSet<String>(rolesList);
		return roles;
	}

	public Set<String> getAccountPermissions(long id) {	
		String sql = "select permission from account_permissions where account_id = " + id;
		List<String> rolesList = jdbcTemplate.queryForList(sql, String.class);
		Set<String> roles = new HashSet<String>(rolesList);
		return roles;
	}

	public Set<String> getAccountPermissions(String username) {	
		Account account = findByUsername(username);
		String sql = "select permission from account_permissions where account_id = " + account.getId();
		List<String> permissionsList = jdbcTemplate.queryForList(sql, String.class);
		Set<String> permissions = new HashSet<String>(permissionsList);
		return permissions;
	}

	public long countQuery(String query){
		String sql = "select count(*) from account where upper(name) like upper('%" + query + "%')";
		long count = jdbcTemplate.queryForObject(sql, new Object[] {}, Long.class);
		return count;
	}

	public List<Account> search(String uncleanedQuery, int offset) {
		String query = uncleanedQuery.replaceAll("([-+.^:,])","");
		String sql = "select distinct * from account where upper(name) like upper(:query) and disabled = false order by name";

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("query", "%" + query + "%")
				.addValue("offset", offset);

		List<Account> accountsSearched = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<Account>(Account.class));
		return accountsSearched;
	}

	public boolean incrementViews(ProfileView view){
		String sql = "insert into profile_views (profile_id, viewer_id, date_viewed) values (?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] {
				view.getProfileId(), view.getViewerId(), view.getDateViewed()
		});
		return true;
	}

	public List<ProfileView> getViewsList(Account account, long start, long end){
		String sql = "select * from profile_views where profile_id = ? and date_viewed between ? and ?";

		List<ProfileView> profileViews = new ArrayList<ProfileView>();

		try {
			profileViews = jdbcTemplate.query(sql, new Object[]{ account.getId(), start, end },
					new BeanPropertyRowMapper<ProfileView>(ProfileView.class));

		}catch (Exception e){ }

		return profileViews;
	}

	public long getViews(Account account, long start, long end){
		String sql = "select count(*) from profile_views where profile_id = ? and date_viewed between ? and ?";
		long count = jdbcTemplate.queryForObject(sql, new Object[] {account.getId(), start, end}, Long.class);
		return count;
	}

	public long getAllViews(Account account){
		String sql = "select count(*) from profile_views where profile_id = ?";
		long count = jdbcTemplate.queryForObject(sql, new Object[] {account.getId()}, Long.class);
		return count;
	}

	public long getAllViewsAll(){
		String sql = "select count(*) from profile_views";
		long count = jdbcTemplate.queryForObject(sql, new Object[] {}, Long.class);
		return count;
	}



	public long likes(long id){
		String sql = "select count(*) from profile_likes where profile_id = ?";
		long count = jdbcTemplate.queryForObject(sql, new Object[]{ id }, Long.class);
		return count;
	}


	public boolean like(ProfileLike profileLike){
		String sql = "insert into profile_likes (profile_id, liker_id, date_liked) values ( ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] {
				profileLike.getProfileId(), profileLike.getLikerId(), profileLike.getDateLiked()
		});
		return true;
	}


	public boolean liked(ProfileLike profileLike){
		String sql = "select * from profile_likes where profile_id = ? and liker_id = ?";
		PostLike existinPostLike = null;

		try {
			existinPostLike = jdbcTemplate.queryForObject(sql,
					new Object[]{
							profileLike.getProfileId(), profileLike.getLikerId()
					}, new BeanPropertyRowMapper<PostLike>(PostLike.class));
		}catch(Exception e){}

		if(existinPostLike != null) return true;
		return false;
	}


	public boolean unlike(ProfileLike profileLike){
		String sql = "delete from profile_likes where profile_id = ? and liker_id = ?";
		jdbcTemplate.update(sql, new Object[] {
				profileLike.getProfileId(), profileLike.getLikerId()
		});
		return true;
	}

	public boolean updateDisabled(Account account) {
		String sql = "update account set disabled = ? where id = ?";
		jdbcTemplate.update(sql, new Object[] {
				account.isDisabled(), account.getId()
		});
		return true;
	}

	public boolean suspend(Account account) {
		String sql = "update account set disabled = true, date_disabled = ? where id = ?";
		jdbcTemplate.update(sql, new Object[] { account.getDateDisabled(), account.getId() });
		return true;
	}

	public boolean renew(Account account) {
		String sql = "update account set disabled = false where id = ?";
		jdbcTemplate.update(sql, new Object[] { account.getId() });
		return true;
	}

	public boolean block(AccountBlock accountBlock){
		String sql = "insert into account_blocks (person_id, blocker_id, date_blocked) values ( ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] {
			accountBlock.getPersonId(), accountBlock.getBlockerId(), accountBlock.getDateBlocked()
		});
		return true;
	}

	public boolean blocked(AccountBlock accountBlock){
		String sql = "select * from account_blocks where person_id = ? and blocker_id = ?";
		AccountBlock existingBlock = null;

		try {
			existingBlock = jdbcTemplate.queryForObject(sql,
					new Object[]{
							accountBlock.getPersonId(), accountBlock.getBlockerId()
					}, new BeanPropertyRowMapper<AccountBlock>(AccountBlock.class));
		}catch(Exception e){
			log.info("error: " + e.getMessage());
		}

		if(existingBlock != null){
			return true;
		}else {
			return false;
		}
	}


	public boolean unblock(AccountBlock accountBlock){
		String sql = "delete from account_blocks where person_id = ? and blocker_id = ?";
		jdbcTemplate.update(sql, new Object[] {
				accountBlock.getPersonId(), accountBlock.getBlockerId()
		});
		return true;
	}

}