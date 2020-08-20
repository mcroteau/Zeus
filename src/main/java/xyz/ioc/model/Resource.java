package xyz.ioc.model;

import java.util.List;

public class Resource {

    long id;

    String uri;

    long accountId;

    long dateAdded;

    long likesCount;

    long sharesCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public long getSharesCount() {
        return sharesCount;
    }

    public void setShares(long sharesCount) {
        this.sharesCount = sharesCount;
    }

}
