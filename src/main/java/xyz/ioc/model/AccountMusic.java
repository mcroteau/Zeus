package xyz.ioc.model;

import org.springframework.context.ApplicationContext;
import xyz.ioc.common.BeanLookup;

import xyz.ioc.dao.MusicDao;

public class AccountMusic {

	private long id;

	private long accountId;
	private long musicFileId;

	private long dateAdded;

	private String title;
	private String artist;
	private String releaseDate;
	private String uri;

	private String duration;
	private int index;

	private boolean isOwnersAccount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setMusicFileId(long musicFileId){
		this.musicFileId = musicFileId;
	}

	public long getMusicFileId(){
		return musicFileId;
	}



	public void setAccountId(long accountId){
		this.accountId = accountId;
	}

	public long getAccountId(){
		return accountId;
	}



	public void setDateAdded(long dateAdded){
		this.dateAdded = dateAdded;
	}

	public long getDateAdded(){
		return dateAdded;
	}



	public void setUri(String uri){
		this.uri = uri;
	}

	public String getUri(){
		return uri;
	}



	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}



	public void setArtist(String artist){
		this.artist = artist;
	}

	public String getArtist(){
		return artist;
	}



	public void setReleaseDate(String releaseDate){
		this.releaseDate = releaseDate;
	}

	public String getReleaseDate(){
		return releaseDate;
	}



	public MusicFile getMusicFile(){
		ApplicationContext applicationContext = BeanLookup.getApplicationContext();
		MusicDao musicDao = (MusicDao)applicationContext.getBean("musicDao");
		return musicDao.get(musicFileId);
	}

	public String getDuration(){
		return this.duration;
	}

    public void setDuration(String duration) {
		this.duration = duration;
    }


	public boolean isOwnersAccount() {
		return isOwnersAccount;
	}

	public void setOwnersAccount(boolean ownersAccount) {
		isOwnersAccount = ownersAccount;
	}


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}