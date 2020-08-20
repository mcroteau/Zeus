package xyz.ioc.model;

public class PostFlag {

    long id;

    long postId;

    long accountId;

    long dateFlagged;

    boolean shared;

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

    public long getDateFlagged() {
        return dateFlagged;
    }

    public void setDateFlagged(long dateFlagged) {
        this.dateFlagged = dateFlagged;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

}
