package xyz.ioc.web;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.ioc.common.Utilities;
import xyz.ioc.common.Constants;
import xyz.ioc.dao.*;
import xyz.ioc.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ResourceController extends BaseController {

    private static final Logger log = Logger.getLogger(ResourceController.class);

    @Autowired
    private Utilities utilities;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private MusicDao musicDao;

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private ResourceDao resourceDao;

    @RequestMapping(value="/resource", method=RequestMethod.GET)
    public String resource(ModelMap model,
                                         HttpServletRequest request,
                                         final RedirectAttributes redirect,
                                         @RequestParam(value="uri", required = false ) String uri){

        if(!authenticated())
            return "redirect:/signin?uri=" + uri;

        model.addAttribute("uri", uri);
        return "resource";
    }


    @RequestMapping(value="/resource/like", method=RequestMethod.POST)
    public String save(ModelMap model, HttpServletRequest request,
                                         final RedirectAttributes redirect,
                                         @RequestParam(value="uri", required = true ) String uri){

        if(!authenticated())
            return "redirect:/signin?uri=" + uri;

        Resource existingResource = resourceDao.get(uri);
        if(existingResource == null){
            Resource resource = new Resource();
            resource.setUri(uri);
            resource.setAccountId(getAuthenticatedAccount().getId());
            resource.setDateAdded(utilities.getCurrentDate());
            resourceDao.save(resource);
        }

        Resource savedResource = resourceDao.get(uri);
        ResourceLike resourceLike = new ResourceLike();
        resourceLike.setResourceId(savedResource.getId());
        resourceLike.setAccountId(getAuthenticatedAccount().getId());
        resourceLike.setDateLiked(utilities.getCurrentDate());

        if(!resourceDao.liked(resourceLike))
            resourceDao.like(resourceLike);

        model.addAttribute("message", "Successfully liked!");
        return "resource_success";
    }


    @RequestMapping(value="/resource/share", method=RequestMethod.POST)
    public String save(ModelMap model, HttpServletRequest request,
                                     final RedirectAttributes redirect,
                                     @RequestParam(value="uri", required = true ) String uri,
                                     @RequestParam(value="comment", required = true ) String comment){

        if(!authenticated())
            return "redirect:/signin?uri=" + uri;

        Resource resource = resourceDao.get(uri);
        if(resource == null){
            Resource r = new Resource();
            r.setUri(uri);
            r.setAccountId(getAuthenticatedAccount().getId());
            r.setDateAdded(utilities.getCurrentDate());
            resourceDao.save(r);
        }

        Resource savedResource = resourceDao.get(uri);

        Post post = new Post();
        post.setAccountId(getAuthenticatedAccount().getId());
        post.setContent("<p class=\"post-comment\" style=\"white-space: pre-line\">" + uri + "<br/>" + comment + "</p>");
        post.setDatePosted(utilities.getCurrentDate());
        Post savedPost = postDao.save(post);

        accountDao.saveAccountPermission(getAuthenticatedAccount().getId(), Constants.POST_MAINTENANCE + savedPost.getId());

        ResourceShare resourceShare = new ResourceShare();
        resourceShare.setComment(comment);
        resourceShare.setResourceId(savedResource.getId());
        resourceShare.setPostId(savedPost.getId());
        resourceShare.setAccountId(getAuthenticatedAccount().getId());
        resourceShare.setDateShared(utilities.getCurrentDate());
        resourceDao.share(resourceShare);

        model.addAttribute("message", "Successfully shared!");

        return "resource_success";
    }

    @RequestMapping(value="/resource/likes", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody String data(ModelMap model,
                                       HttpServletRequest request,
                                       final RedirectAttributes redirect,
                                       @RequestParam(value="uri", required = true ) String uri){

        Resource resource = resourceDao.get(uri);
        long likes = 0;
        if(resource != null){
            likes = resourceDao.likesCount(resource.getId());
        }

        Map<String, Object> data = new HashMap<String, Object>();
        Gson gson = new Gson();

        data.put("likes", likes);

        return gson.toJson(data);
    }


    @RequestMapping(value="/resource/shares", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody String shares(ModelMap model,
                                      HttpServletRequest request,
                                      final RedirectAttributes redirect,
                                      @RequestParam(value="uri", required = false ) String uri){

        Resource resource = resourceDao.get(uri);
        long shares = 0;
        if(resource != null){
            shares = resourceDao.sharesCount(resource.getId());
        }

        Map<String, Object> data = new HashMap<String, Object>();
        Gson gson = new Gson();

        data.put("shares", shares);

        return gson.toJson(data);
    }


    @RequestMapping(value="/search", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody String search(ModelMap model,
                  HttpServletRequest request,
                  final RedirectAttributes redirect,
                  @RequestParam(value="q", required = false ) String q){

        Map<String, Object> data = new HashMap<String, Object>();
        Gson gson = new Gson();

        if(!authenticated()){
            data.put("error", "Authentication required");
            return gson.toJson(data);
        }

        Account account = getAuthenticatedAccount();

        if(q != null){

            List<Account>  accounts = accountDao.search(q, 0);

            for(Account a : accounts){
                a.setIsFriend(friendDao.isFriend(account.getId(), a.getId()));
                a.setInvited(friendDao.invited(account.getId(), a.getId()));

                AccountBlock accountBlock = new AccountBlock();
                accountBlock.setPersonId(account.getId());
                accountBlock.setBlockerId(a.getId());

                boolean blocked = accountDao.blocked(accountBlock);
                log.info("blocked: " + blocked + " : " + account.getId() + ":" + a.getId());
                a.setHidden(blocked);
            }
            Map<String, Object> d = new HashMap<String, Object>();

            for(Account a : accounts){
                if(a.getId() == account.getId()){
                    a.setOwnersAccount(true);
                }
            }

            List<MusicFile> music = musicDao.search(q);
            for(MusicFile musicFile : music){
                if(musicFile.getAccountId() == account.getId()){
                    musicFile.setEditable(true);
                }
            }

            d.put("accounts", accounts);
            d.put("music", music);

            return gson.toJson(d);

        } else {
            return gson.toJson(data);
        }
    }

}
