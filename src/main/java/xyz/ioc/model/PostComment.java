package xyz.ioc.model;

public class PostComment {

    long id;
    long postId;

    long accountId;
    String accountName;
    String accountImageUri;

    long dateCreated;
    String comment;

    boolean commentDeletable;

    long commentId;


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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountImageUri() {
        return accountImageUri;
    }

    public void setAccountImageUri(String accountImageUri) {
        this.accountImageUri = accountImageUri;
    }

    public boolean isCommentDeletable() {
        return commentDeletable;
    }

    public void setCommentDeletable(boolean commentDeletable) {
        this.commentDeletable = commentDeletable;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

}
