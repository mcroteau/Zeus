package xyz.ioc.model;

public class ResourceLike {

    long id;

    long resourceId;

    long accountId;

    long dateLiked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getDateLiked() {
        return dateLiked;
    }

    public void setDateLiked(long dateLiked) {
        this.dateLiked = dateLiked;
    }

}
