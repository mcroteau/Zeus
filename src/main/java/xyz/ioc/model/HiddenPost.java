package xyz.ioc.model;

public class HiddenPost {

    long id;

    long postId;

    long accountId;

    long dateHidden;

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

    public long getDateHidden() {
        return dateHidden;
    }

    public void setDateHidden(long dateHidden) {
        this.dateHidden = dateHidden;
    }
}
