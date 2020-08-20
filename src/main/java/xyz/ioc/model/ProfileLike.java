package xyz.ioc.model;

public class ProfileLike{

    long id;

    long profileId;

    long likerId;

    long dateLiked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public long getLikerId() {
        return likerId;
    }

    public void setLikerId(long likerId) {
        this.likerId = likerId;
    }

    public long getDateLiked() {
        return dateLiked;
    }

    public void setDateLiked(long dateLiked) {
        this.dateLiked = dateLiked;
    }

}