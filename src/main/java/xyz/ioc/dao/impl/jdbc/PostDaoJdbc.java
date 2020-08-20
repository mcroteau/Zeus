package xyz.ioc.dao.impl.jdbc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import xyz.ioc.model.*;
import xyz.ioc.dao.FriendDao;
import xyz.ioc.dao.PostDao;


public class PostDaoJdbc implements PostDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private FriendDao friendDao;


	public long id() {
		String sql = "select max(id) from posts";
		long id = jdbcTemplate.queryForObject(sql, new Object[]{}, Long.class);
		return id;
	}

	public long postCommentId() {
		String sql = "select max(id) from post_comments";
		long id = jdbcTemplate.queryForObject(sql, new Object[]{}, Long.class);
		return id;
	}


	public long postShareCommentId() {
		String sql = "select max(id) from post_share_comments";
		long id = jdbcTemplate.queryForObject(sql, new Object[]{}, Long.class);
		return id;
	}


	public long count() {
		String sql = "select count(*) from posts";
		long count = jdbcTemplate.queryForObject(sql, new Object[] { }, Long.class);
	 	return count; 
	}

	
	public Post get(long id) {
		String sql = "select * from posts where id = ?";
		
		Post post = jdbcTemplate.queryForObject(sql, new Object[] { id }, 
				new BeanPropertyRowMapper<Post>(Post.class));
		
		if(post == null) post = new Post();

		return post;
	}


	public PostComment getPostComment(long id) {
		String sql = "select * from post_comments where id = ?";

		PostComment comment = jdbcTemplate.queryForObject(sql, new Object[] { id },
				new BeanPropertyRowMapper<PostComment>(PostComment.class));

		if(comment == null) comment = new PostComment();

		return comment;
	}


	public PostShareComment getPostShareComment(long id) {
		String sql = "select * from post_share_comments where id = ?";

		PostShareComment comment = jdbcTemplate.queryForObject(sql, new Object[] { id },
				new BeanPropertyRowMapper<PostShareComment>(PostShareComment.class));

		if(comment == null) comment = new PostShareComment();

		return comment;
	}


	public List<Post> feed(long start, long end, long accountId){
		
		List<Friend> friends = friendDao.getFriends(accountId);
		Set<Long> ids = new HashSet<Long>();

		for(Friend connection : friends) {
			ids.add(connection.getFriendId());
		}

		ids.add(accountId);
		String idsString = StringUtils.join(ids, ",");

		String sql = "select p.id, p.account_id, p.content, p.date_posted, p.image_file_uri, p.music_file_uri, p.video_file_uri, p.hidden, p.flagged, " +
					"a.image_uri, a.name, a.username from " +
						"posts p inner join account a on p.account_id = a.id " +
							"where account_id in (" + idsString + ") " +
								"and p.date_posted between " + start + " and " + end + " order by p.date_posted desc";

		List<Post> feed = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Post>(Post.class));

		return feed;
	}



	public List<Post> getLatestPostsSkinny(long start, long end, long accountId){

		List<Friend> friends = friendDao.getFriends(accountId);
		Set<Long> ids = new HashSet<Long>();

		for(Friend connection : friends) {
			ids.add(connection.getFriendId());
		}

		ids.add(accountId);
		String idsString = StringUtils.join(ids, ",");

		String sql = "select a.name from " +
				"posts p inner join account a on p.account_id = a.id " +
				"where account_id in (" + idsString + ") " +
				"and p.date_posted between " + start + " and " + end + "";

		List<Post> latest = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Post>(Post.class));

		return latest;
	}


	@Override
	public List<Post> fetchUserPosts(long accountId) {
		String sql = "select p.id, p.account_id, p.content, p.date_posted, p.image_file_uri, p.music_file_uri, p.video_file_uri, p.hidden, p.flagged, " +
				"a.image_uri, a.name, a.username from " +
				"posts p inner join account a on p.account_id = a.id " +
				"where account_id = ? order by p.date_posted desc";

		List<Post> posts = jdbcTemplate.query(sql, new Object[]{ accountId }, new BeanPropertyRowMapper<Post>(Post.class));

		return posts;
	}


	public Post save(Post post){
		String sql = "insert into posts (account_id, content, video_file_uri, date_posted, hidden, flagged ) values ( ?, ?, ?, ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] { 
			post.getAccountId(), post.getContent(), post.getVideoFileUri(), post.getDatePosted(), post.isHidden(), false
		});
		long id = id();
		Post savedPost = get(id);
		
		return savedPost;
	}


	public boolean update(Post post) {
		String sql = "update posts set content = ? where id = ?";
		jdbcTemplate.update(sql, new Object[] {
			post.getContent(), post.getId()
		});
		return true;
	}

	public boolean hide(long id){
		String sql = "update posts set hidden = true where id = ?";
		jdbcTemplate.update(sql, new Object[] { id });
		return true;
	}


	public boolean delete(long id){
		String sql = "delete from posts where id = ?";
		jdbcTemplate.update(sql, new Object[] { id });
		return true;
	}

	public long likes(long id){
		String sql = "select count(*) from post_likes where post_id = ?";
		long count = jdbcTemplate.queryForObject(sql, new Object[]{ id }, Long.class);
		return count;
	}


	public boolean like(PostLike postLike){
		String sql = "insert into post_likes (post_id, account_id, date_liked) values ( ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] {
				postLike.getPostId(), postLike.getAccountId(), postLike.getDateLiked()
		});
		return true;
	}


	public boolean liked(PostLike postLike){
		String sql = "select * from post_likes where post_id = ? and account_id = ?";
		PostLike existinPostLike = null;

		try {
			existinPostLike = jdbcTemplate.queryForObject(sql,
					new Object[]{
							postLike.getPostId(), postLike.getAccountId()
					}, new BeanPropertyRowMapper<PostLike>(PostLike.class));
		}catch(Exception e){}

		if(existinPostLike != null) return true;
		return false;
	}


	public boolean unlike(PostLike postLike){
		String sql = "delete from post_likes where post_id = ? and account_id = ?";
		jdbcTemplate.update(sql, new Object[] {
				postLike.getPostId(), postLike.getAccountId()
		});
		return true;
	}


	public boolean deletePostLikes(long postId){
		String sql = "delete from post_likes where post_id = ?";
		jdbcTemplate.update(sql, new Object[] { postId });
		return true;
	}


	public List<PostShare> getPostShares(long start, long end, long accountId){

		String sql = "select * from post_shares where account_id in (:ids) and date_shared between " + start + " and " + end + " order by date_shared desc";

		List<Friend> friends = friendDao.getFriends(accountId);
		Set<Long> ids = new HashSet<Long>();
		for(Friend connection : friends) {
			ids.add(connection.getFriendId());
		}
		//adding personal posts
		ids.add(accountId);

		//System.out.println(StringUtils.join(ids, "|"));

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ids", ids);

		List<PostShare> shares = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<PostShare>(PostShare.class));
		return shares;
	}

	@Override
	public List<PostShare> fetchUserPostShares(long accountId) {
		String sql = "select * from post_shares where account_id = ? order by date_shared desc";
		List<PostShare> postShares = jdbcTemplate.query(sql, new Object[]{ accountId }, new BeanPropertyRowMapper<PostShare>(PostShare.class));

		return postShares;
	}


	public long shares(long id){
		String sql = "select count(*) from post_shares where post_id = ?";
		long count = jdbcTemplate.queryForObject(sql, new Object[]{ id }, Long.class);
		return count;
	}


	public boolean share(PostShare postShare){
		String sql = "insert into post_shares (post_id, account_id, comment, date_shared) values ( ?, ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] {
				postShare.getPostId(), postShare.getAccountId(), postShare.getComment(), postShare.getDateShared()
		});
		return true;
	}


	public PostShare getPostShare(long postShareId){
		String sql = "select * from post_shares where id = ?";
		PostShare postShare = null;

		try {
			postShare = jdbcTemplate.queryForObject(sql, new Object[]{ postShareId }, new BeanPropertyRowMapper<PostShare>(PostShare.class));
		}catch(Exception e){}

		return postShare;
	}



	public boolean hasPostShares(long postId){
		List<PostShare> postShares = new ArrayList<PostShare>();

		try {

			String sql = "select * from post_shares where post_id = ?";
			postShares = jdbcTemplate.query(sql, new Object[]{ postId }, new BeanPropertyRowMapper<PostShare>(PostShare.class));

		}catch(Exception e){
			return false;
		}

		if(postShares.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	public boolean deletePostShare(long id) {
		String sql = "delete from post_shares where id = ?";
		jdbcTemplate.update(sql, new Object[] { id });
		return true;
	}


	public boolean removePosts(long id) {
		String sql = "delete from posts where account_id = ?";
		jdbcTemplate.update(sql, new Object[] { id });
		return true;
	}

	public boolean removePostShares(long id) {
		String sql = "delete from post_shares where post_id = ?";
		jdbcTemplate.update(sql, new Object[] { id });
		return true;
	}


	public List<PostComment> getPostComments(long postId){
		List<PostComment> postComments = null;

		try {

			String sql = "select * from post_comments where post_id = ? order by date_created asc";
			postComments = jdbcTemplate.query(sql, new Object[]{ postId }, new BeanPropertyRowMapper<PostComment>(PostComment.class));

			if (postComments == null) postComments = new ArrayList<PostComment>();

		}catch(Exception e){
			e.printStackTrace();
		}

		return postComments;
	}


	public List<PostShareComment> getPostShareComments(long postShareId){
		List<PostShareComment> postShareComments = null;

		try {

			String sql = "select * from post_share_comments where post_share_id = ? order by date_created asc";
			postShareComments = jdbcTemplate.query(sql, new Object[]{ postShareId }, new BeanPropertyRowMapper<PostShareComment>(PostShareComment.class));

			if (postShareComments == null) postShareComments = new ArrayList<PostShareComment>();

		}catch(Exception e){
			e.printStackTrace();
		}

		return postShareComments;
	}


	public PostComment savePostComment(PostComment postComment){
		String sql = "insert into post_comments (post_id, account_id, account_name, account_image_uri, comment, date_created) values ( ?, ?, ?, ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] {
			postComment.getPostId(), postComment.getAccountId(), postComment.getAccountName(), postComment.getAccountImageUri(), postComment.getComment(), postComment.getDateCreated()
		});

		long id = postCommentId();
		PostComment savedComment = getPostComment(id);
		return savedComment;
	}


	public PostShareComment savePostShareComment(PostShareComment postShareComment){
		String sql = "insert into post_share_comments (post_share_id, account_id, account_name, account_image_uri, comment, date_created) values ( ?, ?, ?, ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] {
				postShareComment.getPostShareId(), postShareComment.getAccountId(), postShareComment.getAccountName(), postShareComment.getAccountImageUri(), postShareComment.getComment(), postShareComment.getDateCreated()
		});

		long id = postShareCommentId();
		PostShareComment savedComment = getPostShareComment(id);
		return savedComment;
	}


	public boolean deletePostComment(long id) {
		String sql = "delete from post_comments where id = ?";
		jdbcTemplate.update(sql, new Object[] { id });
		return true;
	}


	public boolean deletePostComments(long postId){
		String sql = "delete from post_comments where post_id = ?";
		jdbcTemplate.update(sql, new Object[] { postId });
		return true;
	}


	public boolean deletePostShareComment(long id) {
		String sql = "delete from post_share_comments where id = ?";
		jdbcTemplate.update(sql, new Object[] { id });
		return true;
	}


	public boolean deletePostShareComments(long postShareId){
		String sql = "delete from post_share_comments where post_share_id = ?";
		jdbcTemplate.update(sql, new Object[] { postShareId });
		return true;
	}

	public List<PostImage> getImages(long id) {
		List<PostImage> postImages = null;

		try {
			String sql = "select * from post_images where post_id = ?";
			postImages = jdbcTemplate.query(sql, new Object[]{ id }, new BeanPropertyRowMapper<PostImage>(PostImage.class));

			if (postImages == null) postImages = new ArrayList<PostImage>();
		}catch(Exception e){
			e.printStackTrace();
		}
		return postImages;
	}

	public boolean saveImage(PostImage postImage) {
		String sql = "insert into post_images (post_id, uri, date_uploaded) values ( ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] {
			postImage.getPostId(), postImage.getUri(), postImage.getDate()
		});
		return true;
	}

	public boolean deletePostImage(long id) {
		String sql = "delete from post_images where id = ?";
		jdbcTemplate.update(sql, new Object[] { id });
		return true;
	}

	@Override
	public List<PostMusic> getMusic(long id) {
		List<PostMusic> postMusics = null;

		try {
			String sql = "select * from post_music where post_id = ?";
			postMusics = jdbcTemplate.query(sql, new Object[]{ id }, new BeanPropertyRowMapper<PostMusic>(PostMusic.class));

			if (postMusics == null) postMusics = new ArrayList<PostMusic>();
		}catch(Exception e){
			e.printStackTrace();
		}
		return postMusics;
	}

	@Override
	public boolean saveMusic(PostMusic postMusic) {
		String sql = "insert into post_music (post_id, account_id, music_file_id, date_uploaded) values ( ?, ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] {
				postMusic.getPostId(), postMusic.getAccountId(), postMusic.getMusicFileId(), postMusic.getDate()
		});
		return true;
	}

	@Override
	public boolean deletePostMusic(long id) {
		String sql = "delete from post_music where id = ?";
		jdbcTemplate.update(sql, new Object[] { id });
		return true;
	}

	public boolean flagPost(PostFlag postFlag) {
		String sql = "insert into post_flags (post_id, account_id, date_flagged, shared) values ( ?, ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] {
				postFlag.getPostId(), postFlag.getAccountId(), postFlag.getDateFlagged(), postFlag.isShared()
		});
		return true;
	}


	public List<Post> getFlaggedPosts(){
		String sql = "select * from posts where flagged = true and hidden = false";
		List<Post> posts = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Post>(Post.class));
		return posts;
	}

	public Post getFlaggedPost(long id){
		String sql = "select * from posts where id = ? and flagged = true and hidden = false";
		Post post = jdbcTemplate.queryForObject(sql, new Object[]{ id }, new BeanPropertyRowMapper<Post>(Post.class));
		return post;
	}


	public boolean updateFlagged(Post post) {
		String sql = "update posts set flagged = ? where id = ?";
		jdbcTemplate.update(sql, new Object[] {
				post.isFlagged(), post.getId()
		});
		return true;
	}

	public boolean removePostFlags(long postId){
		String sql = "delete from post_flags where post_id = ?";
		jdbcTemplate.update(sql, new Object[] { postId });
		return true;
	}

	public boolean makeInvisible(HiddenPost hiddenPost){
		String sql = "insert into hidden_posts (post_id, account_id, date_hidden) values ( ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] {
			hiddenPost.getPostId(), hiddenPost.getAccountId(), hiddenPost.getDateHidden()
		});
		return true;
	}

	public List<HiddenPost> getHiddenPosts(long postId, long accountId) {
		String sql = "select * from hidden_posts where post_id = ? and account_id = ?";
		List<HiddenPost> hiddenPosts = jdbcTemplate.query(sql, new Object[]{ postId, accountId }, new BeanPropertyRowMapper<HiddenPost>(HiddenPost.class));
		return hiddenPosts;
	}
}