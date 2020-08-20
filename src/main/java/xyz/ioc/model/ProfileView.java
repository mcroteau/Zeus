package xyz.ioc.model;

public class ProfileView {

    public static class Builder {

        long id;

        long profileId;

        long viewerId;

        long dateViewed;


        public Builder profile(long profileId){
            this.profileId = profileId;
            return this;
        }

        public Builder viewer(long viewerId){
            this.viewerId = viewerId;
            return this;
        }

        public Builder date(long dateViewed){
            this.dateViewed = dateViewed;
            return this;
        }

        public ProfileView build(){
            ProfileView view = new ProfileView();
            view.profileId = this.profileId;
            view.viewerId = this.viewerId;
            view.dateViewed = this.dateViewed;
            return view;
        }

    }


    long id;

    long profileId;

    long viewerId;

    long dateViewed;

    public long getId() {
        return id;
    }

    public long getProfileId() {
        return profileId;
    }

    public long getViewerId() {
        return viewerId;
    }

    public long getDateViewed() {
        return dateViewed;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public void setViewerId(long viewerId) {
        this.viewerId = viewerId;
    }

    public void setDateViewed(long dateViewed) {
        this.dateViewed = dateViewed;
    }

}