package xyz.ioc.web;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import xyz.ioc.common.Constants;
import xyz.ioc.common.Utilities;
import xyz.ioc.dao.AccountDao;
import xyz.ioc.dao.MessageDao;
import xyz.ioc.model.Account;
import xyz.ioc.model.Message;
import xyz.ioc.model.OutputMessage;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MessageController extends BaseController{

    private static final Logger log = Logger.getLogger(MessageController.class);

    private Gson gson = new Gson();

    @Autowired
    private Utilities utilities;

    @Autowired
    private MessageDao messageDao;


    @Autowired
    private AccountDao accountDao;


    @RequestMapping(value="/chat/info", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody String  info() {
        Map data = new HashMap<String, String>();
        data.put("current_time", utilities.getCurrentDate());
        return gson.toJson(data);
    }


    @RequestMapping(value="/messages/unread", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody String  unread() throws Exception {
        Map data = new HashMap<String, Integer>();
        try {
            Account account = getAuthenticatedAccount();
            int count = messageDao.unread(account.getId());
            data.put("count", count);
        }catch(Exception e){
            data.put("count", 0);
        }

        return gson.toJson(data);
    }


    @RequestMapping(value="/messages/read/{id}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody String read( @PathVariable String id ) {
        Account recipient = getAuthenticatedAccount();
        Account sender = accountDao.get(Long.parseLong(id));

        messageDao.read(sender.getId(), recipient.getId());

        Map data = new HashMap<String, Boolean>();
        data.put("success", true);
        return gson.toJson(data);
    }


    @RequestMapping(value="/messages/{id}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody String messages( @PathVariable String id ) {
        Account recipient = getAuthenticatedAccount();
        Account sender = accountDao.get(Long.parseLong(id));

        List<Message> messages = messageDao.messages(sender.getId(), recipient.getId());
        OutputMessage outputMessage = new OutputMessage();
        try {

            for (Message m : messages) {
                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_SEARCH_FORMAT);
                Date date = format.parse(Long.toString(m.getDateSent()));
                PrettyTime p = new PrettyTime();
                m.setTimeAgo(p.format(date));

                Account s = accountDao.get(m.getSenderId());
                Account r = accountDao.get(m.getRecipientId());

                m.setSender(s.getName());
                m.setRecipient(r.getName());
            }

            outputMessage.setRecipientId(sender.getId());
            outputMessage.setRecipientImageUri(sender.getImageUri());
            outputMessage.setMessages(messages);

        }catch(Exception e){
            return gson.toJson(outputMessage);
        }
        return gson.toJson(outputMessage);
    }


    @RequestMapping(value="/message/send/{id}", method=RequestMethod.POST, produces="application/json")
    public @ResponseBody String  send(@PathVariable String id,
									  Message m) {
        Account sender = getAuthenticatedAccount();
        Account recipient = accountDao.get(Long.parseLong(id));

        long time = utilities.getCurrentDate();

        Message message = new Message();
        message.setSenderId(sender.getId());
        message.setRecipientId(recipient.getId());
        message.setDateSent(time);
        message.setContent(m.getContent());
        messageDao.send(message);

        Map<String, Boolean> d = new HashMap<String, Boolean>();
        d.put("success", true);
        return gson.toJson(d);
    }


    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage sendWebSockets(Message message) {

        List<Message> messages = new ArrayList<Message>();
        OutputMessage outputMessage = new OutputMessage();

        try {

            if (message.getContent() != null &&
                    !message.getContent().equals("")) {
                System.out.println("content " + message.getContent());
                message.setDateSent(utilities.getCurrentDate());
                messageDao.send(message);
            }

            messages = messageDao.messages(message.getSenderId(), message.getRecipientId());

            for(Message m : messages) {
                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_SEARCH_FORMAT);
                Date date = format.parse(Long.toString(m.getDateSent()));
                PrettyTime p = new PrettyTime();
                m.setTimeAgo(p.format(date));

                Account sender = accountDao.get(m.getSenderId());
                Account recipient = accountDao.get(m.getRecipientId());

                m.setSender(sender.getName());
                m.setRecipient(recipient.getName());
            }

            Account recipient = accountDao.get(message.getRecipientId());

            outputMessage.setRecipientId(recipient.getId());
            outputMessage.setRecipient(recipient.getName());
            outputMessage.setRecipientImageUri(recipient.getImageUri());
            outputMessage.setMessages(messages);

            parallelExternalSdk(message, outputMessage);

        }catch(Exception e){
            e.printStackTrace();
        }

        return outputMessage;
    }


    private Boolean parallelExternalSdk(Message message, OutputMessage outputMessage){

        Account sender = accountDao.get(message.getSenderId());
        Account recipient = accountDao.get(message.getRecipientId());

        return true;
    }



//    @MessageMapping("/message")
//    @SendToUser("/queue/reply")
//    public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {
//        return gson.fromJson(message, Map.class).get("name").toString();
//    }
//
//    @MessageExceptionHandler
//    @SendToUser("/queue/errors")
//    public String handleException(Throwable exception) {
//        return exception.getMessage();
//    }

}
