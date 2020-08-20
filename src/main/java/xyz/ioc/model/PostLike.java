package xyz.ioc.model;

public class PostLike {

    private long postId;
	private long accountId;
    private long dateLiked;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getDateLiked() {
        return dateLiked;
    }

    public void setDateLiked(long dateLiked) {
        this.dateLiked = dateLiked;
    }
}