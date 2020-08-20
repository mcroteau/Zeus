package xyz.ioc.dao.impl.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import xyz.ioc.common.Constants;
import xyz.ioc.dao.FriendDao;
import xyz.ioc.model.Account;
import xyz.ioc.model.AccountMusic;
import xyz.ioc.model.Friend;
import xyz.ioc.model.FriendInvite;

public class FriendDaoJdbc implements FriendDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	public long count() {
		String sql = "select count(*) from friends";
		long count = jdbcTemplate.queryForObject(sql, new Object[] { }, Long.class);
	 	return count; 
	}

	public long countInvites() {
		String sql = "select count(*) from friend_invites";
		long count = jdbcTemplate.queryForObject(sql, new Object[] { }, Long.class);
		return count;
	}


	public long countInvitesByAccount(Account authenticatedAccount) {
		String sql = "select count(*) from friend_invites where invited_id = ? and new_invite = ?";
		long count = jdbcTemplate.queryForObject(sql, new Object[] { authenticatedAccount.getId(), Constants.TRUE }, Long.class);
		return count;
	}


	public FriendInvite getFriendInvite(long invitee, long invited){
		FriendInvite friendInvite;
		try {

			String sql = "select * from friend_invites where account_id = ? and invited_id = ?";
			friendInvite = jdbcTemplate.queryForObject(sql, new Object[]{invitee, invited}, FriendInvite.class);

		}catch(Exception e){
			friendInvite = null;
		}
		return friendInvite;
	}


	public List<FriendInvite> invites(long invitedId){
		List<FriendInvite> invites = null;

		try {

			String sql = "select fi.invitee_id, fi.invited_id, " +
					"a.name, a.location, a.age, a.image_uri " +
					"from friend_invites fi inner join account a on fi.invitee_id = a.id " +
					"where invited_id = ? and new_invite = ?";

			invites = jdbcTemplate.query(sql, new Object[]{invitedId, 1}, new BeanPropertyRowMapper<FriendInvite>(FriendInvite.class));

			if (invites == null) invites = new ArrayList<FriendInvite>();

		}catch(Exception e){
			e.printStackTrace();
		}

		return invites;
	}




	public boolean invite(long inviteeId, long invitedId, long dateCreated){

		if(!invited(inviteeId, invitedId)) {
			String sql = "insert into friend_invites (invitee_id, invited_id, date_created, new_invite, accepted, ignored) values (?, ?, ?, ?, ?, ?)";
			try {
				jdbcTemplate.update(sql, new Object[]{
						inviteeId, invitedId, dateCreated, Constants.TRUE, Constants.FALSE, Constants.FALSE
				});
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		return true;
	}






	public boolean invited(long inviteeId, long invitedId){
		String sql = "select * from friend_invites where invitee_id = ? and invited_id = ? and new_invite = true";
		FriendInvite friendInvite = null;

		try {
			friendInvite = jdbcTemplate.queryForObject(sql, new Object[]{inviteeId, invitedId},
					new BeanPropertyRowMapper<FriendInvite>(FriendInvite.class));
		}catch(Exception e){ }


		if(friendInvite != null){
			return true;
		}
		return false;
	}



	public boolean accept(long inviteeId, long invitedId, long currentDate){
		String sql = "update friend_invites set accepted = 1, new_invite = 0 where invitee_id = ? and invited_id = ?";
		try {
			jdbcTemplate.update(sql, new Object[]{
					inviteeId, invitedId
			});
			saveConnection(inviteeId, invitedId, currentDate);
		}catch(Exception e){
			e.printStackTrace();
		}

		return true;
	}



	public boolean ignore(long accountId, long invitedId, long currentDate){
		String sql = "update friend_invites set ignored = 1, new_invite = 0 where invitee_id = ? and invited_id = ?";
		try {

			jdbcTemplate.update(sql, new Object[]{
					accountId, invitedId
			});

		}catch(Exception e){
			e.printStackTrace();
		}

		return true;
	}

	
	public List<Friend> getFriends(long accountId) {
		String sql = "select f.account_id, f.friend_id, a.name, a.age, a.image_uri from friends f inner join account a on f.friend_id = a.id where account_id = ?";

		List<Friend> friends = jdbcTemplate.query(sql, new Object[] { accountId },
				new BeanPropertyRowMapper<Friend>(Friend.class));
		
		return friends;
	}



	public boolean saveConnection(long accountId1, long accountId2, long dateCreated) {
		String sql = "insert into friends (account_id, friend_id, date_created) values (?, ?, ?)";
		
		jdbcTemplate.update(sql, new Object[] { 
			accountId1, accountId2, dateCreated  
		});
		
		jdbcTemplate.update(sql, new Object[] { 
			accountId2, accountId1, dateCreated  
		});
		
		return true;
	}


	public boolean removeConnection(long accountId1, long accountId2) {
		String sql = "delete from friends where account_id = ? and friend_id = ?";

		jdbcTemplate.update(sql, new Object[] {
				accountId1, accountId2
		});

		jdbcTemplate.update(sql, new Object[] {
				accountId2, accountId1
		});

		return true;
	}



	public boolean isFriend(long accountId, long friendId){
		String sql = "select * from friends where account_id = ? and friend_id = ?";
		Friend friend = null;
		try {
			friend = jdbcTemplate.queryForObject(sql, new Object[]{accountId, friendId},
					new BeanPropertyRowMapper<Friend>(Friend.class));
		}catch(Exception e){
			///
		}
		if(friend != null){
			return true;
		}
		return false;
	}



}
