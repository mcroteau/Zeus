package xyz.ioc.dao.impl.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.ioc.dao.MessageDao;
import xyz.ioc.model.Account;
import xyz.ioc.model.Message;
import xyz.ioc.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MessageDaoJdbc implements MessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public long count() {
        String sql = "select count(*) from messages";
        int count = jdbcTemplate.queryForObject(sql, new Object[] { }, Integer.class);
        return count;
    }

    public long countByAccount(Account autheticatedAccount) {
        String sql = "select count(*) from messages where recipient_id = ? and viewed = false";
        int count = jdbcTemplate.queryForObject(sql, new Object[] { autheticatedAccount.getId() }, Integer.class);
        return count;
    }

    @Override
    public long getId() {
        String sql = "select max(id) from messages";
        long id = jdbcTemplate.queryForObject(sql, new Object[]{}, Long.class);
        return id;
    }

    @Override
    public Message send(Message message) {
        String sql = "insert into messages (sender_id, recipient_id, content, date_sent, viewed) values (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, new Object[]{
                    message.getSenderId(), message.getRecipientId(), message.getContent(), message.getDateSent(), false
            });
        }catch(Exception e){
            e.printStackTrace();
        }

        String selectSql = "select * from messages where id = ?";
        Message persistedMessage = jdbcTemplate.queryForObject(selectSql, new Object[] { getId() },
                new BeanPropertyRowMapper<Message>(Message.class));

        return persistedMessage;
    }


    @Override
    public List<Message> messages(long partipantUno, long partipantDos) {

        String sqlUno = "select * from messages where sender_id = ? and recipient_id = ?";

        List<Message> messagesUno = jdbcTemplate.query(sqlUno, new Object[] { partipantUno, partipantDos },
                new BeanPropertyRowMapper<Message>(Message.class));

        String sqlDos = "select * from messages where sender_id = ? and recipient_id = ?";

        List<Message> messagesDos = jdbcTemplate.query(sqlDos, new Object[] { partipantDos, partipantUno },
                new BeanPropertyRowMapper<Message>(Message.class));

        List<Message> messages = new ArrayList<Message>(messagesUno);
        messages.addAll(messagesDos);

        Comparator<Message> comparator = new Comparator<Message>() {
            @Override
            public int compare(Message a1, Message a2) {
                Long p1 = new Long(a1.getDateSent());
                Long p2 = new Long(a2.getDateSent());
                return p1.compareTo(p2);
            }
        };

        Collections.sort(messages, comparator);

        return messages;
    }


    @Override
    public boolean hasMessages(long sender, long recipient) {
        String sql = "select count(*) from messages where viewed = false and sender_id = ? and recipient_id = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[] { sender, recipient }, Integer.class);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public int unread(long recipientId) {
        String sql = "select count(*) from messages where viewed = false and recipient_id = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[] { recipientId }, Integer.class);
        return count;
    }


    @Override
    public boolean read(long sender, long recipient){
        String sql = "update messages set viewed = true where sender_id = ? and recipient_id = ?";
        try {
            jdbcTemplate.update(sql, new Object[]{ sender, recipient });
        }catch(Exception e){
            e.printStackTrace();
        }

        return true;
    }
}
