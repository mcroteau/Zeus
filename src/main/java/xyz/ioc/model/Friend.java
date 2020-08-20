package xyz.ioc.model;

import org.springframework.context.ApplicationContext;

import xyz.ioc.common.BeanLookup;
import xyz.ioc.dao.AccountDao;

public class Friend {

	long id;

	long accountId;

	long friendId;

	String name;

	String age;

	String imageUri;

	long dateAccepted;
	
	long requestId;

	boolean messages;


	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	
	public long getFriendId() {
		return friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public long getDateAccepted() {
		return dateAccepted;
	}

	public void setDateAccepted(long dateAccepted) {
		this.dateAccepted = dateAccepted;
	}
	
	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public boolean hasMessages() {
		return messages;
	}

	public void setHasMessages(boolean messages) {
		this.messages = messages;
	}

}
