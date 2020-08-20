package xyz.ioc.model;

import java.util.List;

public class Post {

	long id;

	long accountId;

	String content;

	long datePosted;

	String timeAgo;

	Account account;

	private long likes;

	private boolean liked;

	private long shares;

	private boolean shared;

	private long sharedAccountId;

	private String sharedComment;

	private String sharedAccount;

	private String timeSharedAgo;

	private String imageUri;

	private String name;

	private String username;

	private String sharedImageUri;

	private List<String> imageFileUris;

	private List<String> musicFileUris;

	private String musicFileUri;

    private String videoFileUri;

    private String status;

	private List<PostComment> comments;

	private List<PostShareComment> shareComments;

	boolean deletable;

	boolean hidden;

	long postShareId;

	boolean commentsOrShareComments;

	boolean flagged;


	public long getId(){
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getContent() {
		return content;
	}	

	public void setContent(String content) {
		this.content = content;
	}

	public long getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(long datePosted) {
		this.datePosted = datePosted;
	}

	public String getTimeAgo() {
		return timeAgo;
	}

	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}

	public long getLikes() {
		return likes;
	}

	public void setLikes(long likes) {
		this.likes = likes;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public long getShares() {
		return shares;
	}

	public void setShares(long shares) {
		this.shares = shares;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public long getSharedAccountId() {
		return sharedAccountId;
	}

	public void setSharedAccountId(long sharedAccountId) {
		this.sharedAccountId = sharedAccountId;
	}

	public String getSharedComment() {
		return sharedComment;
	}

	public void setSharedComment(String sharedComment) {
		this.sharedComment = sharedComment;
	}

	public String getSharedAccount() {
		return sharedAccount;
	}

	public void setSharedAccount(String sharedAccount) {
		this.sharedAccount = sharedAccount;
	}

	public String getTimeSharedAgo() {
		return timeSharedAgo;
	}

	public void setTimeSharedAgo(String timeSharedAgo) {
		this.timeSharedAgo = timeSharedAgo;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) { this.name = name; }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSharedImageUri() {
		return sharedImageUri;
	}

	public void setSharedImageUri(String sharedImageUri) {
		this.sharedImageUri = sharedImageUri;
	}

	public List<String> getImageFileUris() {
		return imageFileUris;
	}

	public void setImageFileUris(List<String> imageFileUris) {
		this.imageFileUris = imageFileUris;
	}

	public List<String> getMusicFileUris() {
		return musicFileUris;
	}

	public void setMusicFileUris(List<String> musicFileUris) {
		this.musicFileUris = musicFileUris;
	}

    public String getVideoFileUri() {
        return videoFileUri;
    }

    public void setVideoFileUri(String videoFileUri) {
        this.videoFileUri = videoFileUri;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<PostComment> getComments() {
		return comments;
	}

	public void setComments(List<PostComment> comments) {
		this.comments = comments;
	}

	public List<PostShareComment> getShareComments() {
		return shareComments;
	}

	public void setShareComments(List<PostShareComment> shareComments) {
		this.shareComments = shareComments;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public long getPostShareId() {
		return postShareId;
	}

	public void setPostShareId(long postShareId) {
		this.postShareId = postShareId;
	}

	public void setCommentsOrShareComments(boolean commentsOrShareComments) {
		this.commentsOrShareComments = commentsOrShareComments;
	}

	public boolean isCommentsOrShareComments() {
		return this.commentsOrShareComments;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}

}