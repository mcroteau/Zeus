package xyz.ioc.web;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

import java.util.*;

import xyz.ioc.model.Account;
import xyz.ioc.dao.MusicDao;
import xyz.ioc.model.MusicFile;
import xyz.ioc.model.AccountMusic;


import xyz.ioc.common.Utilities;


@Controller
public class MusicController extends BaseController {

	private static final Logger log = Logger.getLogger(MusicController.class);

	@Autowired
	private MusicDao musicDao;

	@Autowired
	private Utilities utilities;


	@RequestMapping(value="/music/playlist/{{id}}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String profilePlaylist(ModelMap model,
										 HttpServletRequest request,
										 final RedirectAttributes redirect,
										 @PathVariable String id){

		Map<String, Object> data = new HashMap<String, Object>();
		Gson gson = new Gson();

		if(!authenticated()){
			data.put("error", true);
			return gson.toJson(data);
		}

		Account account = getAuthenticatedAccount();
		List<AccountMusic> accountMusics = musicDao.getAccountMusics(Long.parseLong(id));

		List<AccountMusic> playlist = new ArrayList<AccountMusic>();
		for(AccountMusic accountMusic: accountMusics) {
			MusicFile musicFile = musicDao.get(accountMusic.getMusicFileId());
			accountMusic.setTitle(musicFile.getTitle());
			accountMusic.setArtist(musicFile.getArtist());
			accountMusic.setReleaseDate(musicFile.getReleaseDate());
			accountMusic.setDuration(musicFile.getDuration());
			accountMusic.setUri(musicFile.getUri());
			playlist.add(accountMusic);
		}

		return gson.toJson(playlist);
	}


	@RequestMapping(value="/music/playlist", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String playlist(ModelMap model,
					HttpServletRequest request,
								final RedirectAttributes redirect){

		Map<String, Object> data = new HashMap<String, Object>();
		Gson gson = new Gson();

		if(!authenticated()){
			data.put("error", true);
			return gson.toJson(data);
		}


		Account account = getAuthenticatedAccount();
		List<AccountMusic> accountMusics = musicDao.getAccountMusics(account.getId());

		int index = 0;
		List<AccountMusic> playlist = new ArrayList<AccountMusic>();
		for(AccountMusic accountMusic: accountMusics) {
			MusicFile musicFile = musicDao.get(accountMusic.getMusicFileId());
			accountMusic.setTitle(musicFile.getTitle());
			accountMusic.setArtist(musicFile.getArtist());
			accountMusic.setReleaseDate(musicFile.getReleaseDate());
			accountMusic.setDuration(musicFile.getDuration());
			accountMusic.setUri(musicFile.getUri());
			accountMusic.setIndex(index);
			playlist.add(accountMusic);
			index++;
		}

		return gson.toJson(playlist);
	}



	@RequestMapping(value="/music/playlist/{id}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String playlist(ModelMap model,
										 HttpServletRequest request,
										 final RedirectAttributes redirect,
										 @PathVariable String id){

		Map<String, Object> data = new HashMap<String, Object>();
		Gson gson = new Gson();

		if(!authenticated()){
			data.put("error", true);
			return gson.toJson(data);
		}


		Account account = getAuthenticatedAccount();
		List<AccountMusic> accountMusics = musicDao.getAccountMusics(Long.parseLong(id));

		List<AccountMusic> playlist = new ArrayList<AccountMusic>();
		for(AccountMusic accountMusic: accountMusics) {
			if((account.getId() == Long.parseLong(id)))accountMusic.setOwnersAccount(true);

			MusicFile musicFile = musicDao.get(accountMusic.getMusicFileId());
			accountMusic.setTitle(musicFile.getTitle());
			accountMusic.setArtist(musicFile.getArtist());
			accountMusic.setReleaseDate(musicFile.getReleaseDate());
			accountMusic.setDuration(musicFile.getDuration());
			accountMusic.setUri(musicFile.getUri());
			playlist.add(accountMusic);
		}

		return gson.toJson(playlist);
	}


	@RequestMapping(value="/music/playlist/add/{id}", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String add(ModelMap model,
					   		HttpServletRequest request,
					  	 	final RedirectAttributes redirect,
					     	@PathVariable String id){

		Map<String, Object> data = new HashMap<String, Object>();
		Gson gson = new Gson();

		if(!authenticated()){
			data.put("error", true);
			return gson.toJson(data);
		}

		Account account = getAuthenticatedAccount();

		AccountMusic accountMusic = new AccountMusic();
		long date = utilities.getCurrentDate();

		accountMusic.setAccountId(account.getId());
		accountMusic.setMusicFileId(Long.parseLong(id));
		accountMusic.setDateAdded(date);

		if(!musicDao.addPlaylist(accountMusic)){
			data.put("error", true);
			return gson.toJson(data);
		}
		else{
			data.put("success", true);
		}

		String success = gson.toJson(data);
		return success;
	}






	@RequestMapping(value="/music/playlist/remove/{id}", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody String remove(ModelMap model,
					   			HttpServletRequest request,
					  	 			final RedirectAttributes redirect,
					     				@PathVariable String id){


		Map<String, Object> data = new HashMap<String, Object>();
		Gson gson = new Gson();

		if(!authenticated()){
			data.put("error", true);
			String error = gson.toJson(data);
			return error;
		}

		Account account = getAuthenticatedAccount();
		
		AccountMusic accountMusic = new AccountMusic();
		accountMusic.setId(Long.parseLong(id));
		accountMusic.setAccountId(account.getId());
		accountMusic.setMusicFileId(Long.parseLong(id));

		boolean removed = musicDao.remove(accountMusic);

		if(!removed){
			data.put("error", true);
			String error = gson.toJson(data);
			return error;
		}

		data.put("success", true);
		return gson.toJson(data);
	}



	@RequestMapping(value="/music/upload", method=RequestMethod.POST)
	@ResponseBody
	public String upload(ModelMap model,
					   		 HttpServletRequest request,
					  	 	 final RedirectAttributes redirect,
									  @RequestParam(value="musicFile", required = true) CommonsMultipartFile uploadedMusicFile){

		Map<String, Object> data = new HashMap<String, Object>();
		Gson gson = new Gson();

		if(!authenticated()){
			data.put("error", "not authenticated.");
			String error = gson.toJson(data);
			return error;
		}

		Account account = getAuthenticatedAccount();

		if(uploadedMusicFile != null &&
				!uploadedMusicFile.isEmpty()) {

			String musicFileUri = utilities.writeMp3(uploadedMusicFile, "media/music/");
			if(musicFileUri.equals("")){
				utilities.deleteUploadedFile(musicFileUri);
				redirect.addFlashAttribute("message", "Something went wrong while performing upload. Please make sure file is in mp3 format.");
				return "redirect:/";
			}

			MusicFile musicFile = new MusicFile();//Want music? Apollo

			if(musicDao.exists(musicFile.getArtist(), musicFile.getTitle())){
				data.put("error", "Song already exists, please try searching for it.");
				return gson.toJson(data);
			}

			if(!musicFile.getTitle().equals("")){

				musicFile.setAccountId(account.getId());
				musicDao.save(musicFile);
                MusicFile savedMusicFile = musicDao.get(musicFile.getArtist(), musicFile.getTitle());

                AccountMusic accountMusic = new AccountMusic();
                accountMusic.setAccountId(account.getId());
                accountMusic.setMusicFileId(savedMusicFile.getId());
                accountMusic.setDateAdded(utilities.getCurrentDate());
				musicDao.addPlaylist(accountMusic);

            }
			else {
				data.put("error", "song title cannot be blank");
				return gson.toJson(data);
			}
		}else{
			data.put("error", "empty music file");
			return gson.toJson(data);
		}

		data.put("success", true);

		return gson.toJson(data);
	}


	@RequestMapping(value="/music/search", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String search(ModelMap model,
				       HttpServletRequest request,
					   final RedirectAttributes redirect,
					   @RequestParam(value="q", required = false ) String searchQuery){

		Gson gson = new Gson();
		if(!authenticated()){
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("error", "not authenticated");
			return gson.toJson(data);
		}
		List<MusicFile> musics = musicDao.search(searchQuery);

		return gson.toJson(musics);
	}

}