package xyz.ioc.model;

public class Notification {

	long id;
	long postId;
	long postAccountId;
	long authenticatedAccountId;
	String name;
	long dateCreated;
	boolean liked;
	boolean shared;
	boolean commented;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public long getPostAccountId() {
		return postAccountId;
	}

	public void setPostAccountId(long postAccountId) {
		this.postAccountId = postAccountId;
	}

	public long getAuthenticatedAccountId() {
		return authenticatedAccountId;
	}

	public void setAuthenticatedAccountId(long authenticatedAccountId) {
		this.authenticatedAccountId = authenticatedAccountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public boolean isCommented() {
		return commented;
	}

	public void setCommented(boolean commented) {
		this.commented = commented;
	}

}