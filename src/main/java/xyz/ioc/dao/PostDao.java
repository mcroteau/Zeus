package xyz.ioc.dao;

import java.util.List;

import xyz.ioc.model.*;

public interface PostDao {

	public long id();

	public long count();
	
	public Post get(long id);
	
	public List<Post> feed(long start, long end, long accountId);

	public List<Post> getLatestPostsSkinny(long start, long end, long accountId);

	public List<Post> fetchUserPosts(long accountId);
	
	public Post save(Post post);

	public boolean update(Post post);

	public boolean hide(long id);

	public boolean delete(long id);

	public long likes(long id);

	public boolean like(PostLike postLike);

	public boolean liked(PostLike postLike);

	public boolean unlike(PostLike postLike);

	public boolean deletePostLikes(long postId);

	public long shares(long id);

	public boolean share(PostShare postShare);

	public List<PostShare> getPostShares(long start, long end, long accountId);

	public List<PostShare> fetchUserPostShares(long accountId);

	public PostShare getPostShare(long postShareId);

	public boolean hasPostShares(long postId);

	public boolean deletePostShare(long id);

	public boolean removePosts(long id);

	public boolean removePostShares(long id);

	public List<PostComment> getPostComments(long id);

	public List<PostShareComment> getPostShareComments(long id);

	public PostComment savePostComment(PostComment postComment);

	public PostShareComment savePostShareComment(PostShareComment postShareCommentComment);

	public boolean deletePostComment(long id);

	public boolean deletePostComments(long postId);

	public boolean deletePostShareComment(long id);

	public boolean deletePostShareComments(long postShareId);//TODO:

	public List<PostImage> getImages(long id);

	public boolean saveImage(PostImage postImage);

	public boolean deletePostImage(long id);

	public List<PostMusic> getMusic(long id);

	public boolean saveMusic(PostMusic postMusic);

	public boolean deletePostMusic(long id);

	public boolean flagPost(PostFlag postFlag);

	public List<Post> getFlaggedPosts();

	public Post getFlaggedPost(long id);

	public boolean updateFlagged(Post post);

	public boolean removePostFlags(long postId);

	public boolean makeInvisible(HiddenPost hiddenPost);

	public List<HiddenPost> getHiddenPosts(long postId, long accountId);

}