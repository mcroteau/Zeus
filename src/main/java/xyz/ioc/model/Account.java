package xyz.ioc.model;


public class Account {

	long id;

	String uuid;

	String name;

	String imageUri;

	String username;

	String password;

	String passwordConfirm;

	String location;

	String age;

	boolean isFriend;

	boolean isOwnersAccount;

	boolean invited;

	long views;

	long likes;

	boolean liked;

	boolean disabled;

	long dateDisabled;

	boolean hidden;

	boolean blocked;

	public long getId(){
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getAge() { return age; }

	public void setAge(String age) {
		this.age = age;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setIsFriend(boolean isFriend){ this.isFriend = isFriend; }

	public boolean getIsFriend(){ return isFriend; }

	public boolean isOwnersAccount() {
		return isOwnersAccount;
	}

	public void setOwnersAccount(boolean isOwnersAccount) {
		this.isOwnersAccount = isOwnersAccount;
	}

	public boolean isInvited() {
		return invited;
	}

	public void setInvited(boolean invited) {
		this.invited = invited;
	}

	public long getViews() {
		return views;
	}

	public void setViews(long views) {
		this.views = views;
	}

	public long getLikes() {
		return likes;
	}

	public void setLikes(long likes) {
		this.likes = likes;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public long getDateDisabled() {
		return dateDisabled;
	}

	public void setDateDisabled(long dateDisabled) {
		this.dateDisabled = dateDisabled;
	}

	public boolean getHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	public String getNameUsername(){
		if(name != null && !name.equals("")) return name;
		return username;
	}

	public String toString(){
		return this.id + ": " + this.name + " " + this.username + " ";
	}

}

