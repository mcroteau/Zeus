package xyz.ioc.model;

public class ResourceShare {

    long id;

    long postId;

    long resourceId;

    long accountId;

    String comment;

    long dateShared;


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

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getDateShared() {
        return dateShared;
    }

    public void setDateShared(long dateShared) {
        this.dateShared = dateShared;
    }

}
