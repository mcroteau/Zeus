package xyz.ioc.dao.impl.jdbc;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import xyz.ioc.model.AccountMusic;
import xyz.ioc.model.MusicFile;
import xyz.ioc.dao.MusicDao;


public class MusicDaoJdbc implements MusicDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public long id() {
		String sql = "select max(id) from musics";
		long id = jdbcTemplate.queryForObject(sql, new Object[]{}, Long.class);
		return id;
	}


	public long count() {
		String sql = "select count(*) from musics";
		long count = jdbcTemplate.queryForObject(sql, new Object[] { }, Long.class);
	 	return count; 
	}


	public long getCountAccountMusics(long accountId){
		String sql = "select count(*) from musics where account_id = ?";
		long count = jdbcTemplate.queryForObject(sql, new Object[] { accountId }, Long.class);
		return count;
	}

	
	public MusicFile get(long id) {
		String sql = "select * from musics where id = ?";
		
		MusicFile music = jdbcTemplate.queryForObject(sql, new Object[] { id }, 
				new BeanPropertyRowMapper<MusicFile>(MusicFile.class));
		
		if(music == null) music = new MusicFile();

		return music;
	}

	public MusicFile get(String artist, String title) {
		String sql = "select * from musics where artist = ? and title = ?";
		MusicFile music = null;
		try {
			music = jdbcTemplate.queryForObject(sql, new Object[]{artist, title},
					new BeanPropertyRowMapper<MusicFile>(MusicFile.class));
		}catch(Exception e){
			return null;
		}
		return music;
	}

	public boolean exists(String artist, String title) {
		String sql = "select * from musics where artist = ? and title = ?";

		try {
			MusicFile music = jdbcTemplate.queryForObject(sql, new Object[]{artist, title},
					new BeanPropertyRowMapper<MusicFile>(MusicFile.class));
		}catch(Exception e){
			return false;
		}
		return true;
	}


	public AccountMusic getAccountMusic(long id) {
		String sql = "select * from musics where id = ?";
		
		AccountMusic music = jdbcTemplate.queryForObject(sql, new Object[] { id }, 
				new BeanPropertyRowMapper<AccountMusic>(AccountMusic.class));
		
		if(music == null) music = new AccountMusic();

		return music;
	}


	public List<MusicFile> searchDatatables() {
		String sql = "select * from musics";
		List<MusicFile> musics = jdbcTemplate.query(sql, new BeanPropertyRowMapper<MusicFile>(MusicFile.class));
		return musics;
	}


	public List<MusicFile> search(String uncleanedQuery) {
		String query = uncleanedQuery.replaceAll("([-+.^:,])","");
		String sql1 = "select distinct * from musics where upper(title) like upper('%" + query + "%') order by title asc";
		String sql2 = "select distinct * from musics where upper(artist) like upper('%" + query + "%') order by title asc";

		List<MusicFile> musics1 = jdbcTemplate.query(sql1, new BeanPropertyRowMapper<MusicFile>(MusicFile.class));
		List<MusicFile> musics2 = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<MusicFile>(MusicFile.class));

		Set<MusicFile> musicsSet = new HashSet<MusicFile>(musics1);
		musicsSet.addAll(musics2);

		List<MusicFile> musics = new ArrayList<MusicFile>(musicsSet);

		return musics;
	}


	public List<AccountMusic> getAccountMusics(long id) {

		String sql = "select * from account_musics where account_id = ? order by id desc";
		List<AccountMusic> musics = jdbcTemplate.query(sql, new Object[]{ id }, new BeanPropertyRowMapper<AccountMusic>(AccountMusic.class));
		
		if(musics == null) musics = new ArrayList<AccountMusic>();

		return musics;
	}
	

	public MusicFile save(MusicFile musicFile){

		String artist = musicFile.getArtist();
		String title = musicFile.getTitle();

		String sql = "insert into musics ( account_id, uri, title, artist, release_date, duration, date_uploaded) values ( ?, ?, ?, ?, ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] { 
			musicFile.getAccountId(), musicFile.getUri(), title, artist, musicFile.getReleaseDate(), musicFile.getDuration(), musicFile.getDateUploaded()
		});
		long id = id();
		MusicFile savedFile = get(id);
		return savedFile;
	}



	public boolean addPlaylist(AccountMusic accountMusic){
		String sql = "insert into account_musics (account_id, music_file_id, date_added) values ( ?, ?, ? )";
		jdbcTemplate.update(sql, new Object[] { 
			accountMusic.getAccountId(), accountMusic.getMusicFileId(), accountMusic.getDateAdded()  
		});
		return true;
	}


	

	public boolean remove(AccountMusic accountMusic) {
		String sql = "delete from account_musics where id = ?";
		jdbcTemplate.update(sql, new Object[] { accountMusic.getId() });
		return true;
	}



	public long countByQuery(String query){
		String sql = "select count(*) from musics";///TODO:Get working
		long count = jdbcTemplate.queryForObject(sql, new Object[] { }, Long.class);
		return count;
	}
	

}