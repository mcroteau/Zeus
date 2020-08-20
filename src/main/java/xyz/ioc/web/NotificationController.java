package xyz.ioc.web;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.ioc.dao.NotificationDao;
import xyz.ioc.model.Account;
import xyz.ioc.model.Notification;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class NotificationController extends BaseController {

    private static final Logger log = Logger.getLogger(NotificationController.class);

    @Autowired
    private NotificationDao notificationDao;

    @RequestMapping(value="/notifications", method= RequestMethod.GET, produces="application/json")
    public @ResponseBody
    String messages(HttpServletRequest request){

        Gson gson = new Gson();
        Map<String, Object> response = new HashMap<String, Object>();

        if(!authenticated()){
            response.put("error", "authentication required");
            String responseData = gson.toJson(response);
            return responseData;
        }

        try {

            Account account = getAuthenticatedAccount();

            List<Notification> notifications = notificationDao.notifications(account.getId());
            for(Notification notification: notifications){
                Account a = accountDao.get(notification.getAuthenticatedAccountId());
                notification.setName(a.getName());
            }

            Comparator<Notification> comparator = new Comparator<Notification>() {
                @Override
                public int compare(Notification a1, Notification a2) {
                    Long p1 = new Long(a1.getDateCreated());
                    Long p2 = new Long(a2.getDateCreated());
                    return p2.compareTo(p1);
                }
            };

            Collections.sort(notifications, comparator);
            return gson.toJson(notifications);

        }catch(Exception e){
            e.printStackTrace();
            response.put("error", "something went wrong notifications");
            String responseData = gson.toJson(response);
            return responseData;
        }
    }

    @RequestMapping(value="/notifications/clear", method=RequestMethod.DELETE,  produces="application/json")
    public @ResponseBody String delete(ModelMap model,
                                       HttpServletRequest request,
                                       final RedirectAttributes redirect){

        Map<String, Object> data = new HashMap<String, Object>();
        Gson gson = new Gson();

        if(!authenticated()){
            data.put("error", "authentication required");
            return gson.toJson(data);
        }

        Account account = getAuthenticatedAccount();

        if(notificationDao.clearNotifications(account.getId())){
            data.put("success", true);
        }
        else{
            data.put("error", true);
        }

        return gson.toJson(data);
    }
}
