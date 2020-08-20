package xyz.ioc.model;

import java.util.Date;

//musics 
public class MusicFile {
	
	long id;
	long accountId;
	
	String uri;
	String title;
	String artist;
	String releaseDate;

	long dateUploaded;

	String duration;

	boolean editable;


	public void setId(long id){
		this.id = id;
	}

	public long getId(){
		return id;
	}


	public void setAccountId(long accountId){
		this.accountId = accountId;
	}

	public long getAccountId(){
		return accountId;
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


	public void setDateUploaded(long dateUploaded){
		this.dateUploaded = dateUploaded;
	}

	public long getDateUploaded(){
		return dateUploaded;
	}


	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}


	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public int hashCode(){
		int hash = (this.title + this.artist).hashCode();
		System.out.println("h:" + hash);
		return hash;
	}

	@Override
	public boolean equals(Object obj){
		if (obj == null) {
			return false;
		}

		if (!MusicFile.class.isAssignableFrom(obj.getClass())) {
			return false;
		}

		final MusicFile other = (MusicFile) obj;
		if(this.title.equals(other.getTitle()) &&
				this.artist.equals(other.getArtist()))
					return true;


		return false;
	}

}