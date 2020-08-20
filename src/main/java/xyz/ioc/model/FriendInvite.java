package xyz.ioc.model;

public class FriendInvite {

    long inviteeId;

    long invitedId;

    String newInvite;

    String accepted;

    String ignored;

    long dateCreated;

    long dateAccepted;

    long dateIgnored;


    private String name;

    private String imageUri;

    private String username;

    private String location;

    private String age;

    boolean isOwnersAccount;


    public long getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(long inviteeId) {
        this.inviteeId = inviteeId;
    }

    public long getInvitedId() {
        return invitedId;
    }

    public void setInvitedId(long invitedId) {
        this.invitedId = invitedId;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }


    public String getNewInvite() {
        return newInvite;
    }

    public void setNewInvite(String newInvite) {
        this.newInvite = newInvite;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getIgnored() {
        return ignored;
    }

    public void setIgnored(String ignored) {
        this.ignored = ignored;
    }

    public long getDateAccepted() {
        return dateAccepted;
    }

    public void setDateAccepted(long dateAccepted) {
        this.dateAccepted = dateAccepted;
    }

    public long getDateIgnored() {
        return dateIgnored;
    }

    public void setDateIgnored(long dateIgnored) {
        this.dateIgnored = dateIgnored;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public boolean isOwnersAccount() {
        return isOwnersAccount;
    }

    public void setOwnersAccount(boolean ownersAccount) {
        isOwnersAccount = ownersAccount;
    }
}
