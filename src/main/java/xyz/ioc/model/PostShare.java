package xyz.ioc.model;

import java.util.List;

public class PostShare {

    private long id;

    private long postId;

    private long accountId;

    private String comment;

    private long dateShared;

    private Post post;


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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}