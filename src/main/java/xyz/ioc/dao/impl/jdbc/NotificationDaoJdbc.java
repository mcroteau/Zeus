package xyz.ioc.dao.impl.jdbc;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.ioc.dao.NotificationDao;
import xyz.ioc.model.Account;
import xyz.ioc.model.Notification;

import java.util.List;

public class NotificationDaoJdbc implements NotificationDao {

    private static final Logger log = Logger.getLogger(NotificationDaoJdbc.class);

    @Autowired
    public JdbcTemplate jdbcTemplate;


    public long count() {
        String sql = "select count(*) from notifications";
        long count = jdbcTemplate.queryForObject(sql, new Object[] { }, Long.class);
        return count;
    }


    public long countByAccount(Account authenticatedAccount) {
        String sql = "select count(*) from notifications where notification_account_id = ?";
        long count = jdbcTemplate.queryForObject(sql, new Object[]{ authenticatedAccount.getId() }, Long.class);
        return count;
    }

    public Notification get(int id) {
        String sql = "select * from notifications where id = ?";
        Notification notification = jdbcTemplate.queryForObject(sql, new Object[] { id },
                new BeanPropertyRowMapper<Notification>(Notification.class));
        return notification;
    }

    public boolean save(Notification notification) {
        // log.info("save...");
        String sql = "insert into notifications (post_id, authenticated_account_id, notification_account_id, date_created, liked, shared, commented) values(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, new Object[] {
                notification.getPostId(), notification.getAuthenticatedAccountId(), notification.getPostAccountId(), notification.getDateCreated(), notification.isLiked(), notification.isShared(), notification.isCommented()
        });

        String countSql = "select count(*) from notifications where notification_account_id = ?";
        long count = jdbcTemplate.queryForObject(countSql, new Object[]{ notification.getPostAccountId() }, Long.class);

        // log.info("saved " + count);

        return true;
    }

    public List<Notification> notifications(long authenticatedAccountId) {
        String sql = "select * from notifications where notification_account_id = ?";
        List<Notification> notifications = jdbcTemplate.query(sql, new Object[]{ authenticatedAccountId }, new BeanPropertyRowMapper<Notification>(Notification.class));
        return notifications;
    }


    public boolean clearNotifications(long notificationAccountId) {
        String sql = "delete from notifications where notification_account_id = ?";
        jdbcTemplate.update(sql, new Object[] { notificationAccountId });
        return true;
    }

    public Notification getLikeNotification(long postId, long postAccountId, long authenticatedAccountId){
        String sql = "select * from notifications where post_id = ? and notification_account_id = ? and authenticated_account_id = ? and liked = true";
        Notification notification = null;
        try{
            notification = jdbcTemplate.queryForObject(sql, new Object[] { postId, postAccountId, authenticatedAccountId  },
                    new BeanPropertyRowMapper<Notification>(Notification.class));
        }catch(Exception e){
            // log.error("error getting like notification: " + e.getMessage());
        }
        return notification;
    }

    public Notification getShareNotification(long postId, long postAccountId, long authenticatedAccountId){
        String sql = "select * from notifications where post_id = ? and notification_account_id = ? and authenticated_account_id = ? and shared = true";
        Notification notification = null;
        try{
            notification = jdbcTemplate.queryForObject(sql, new Object[] { postId, postAccountId, authenticatedAccountId  },
                    new BeanPropertyRowMapper<Notification>(Notification.class));
        }catch(Exception e){
            // log.error("error getting share notification: " + e.getMessage());
        }
        return notification;
    }

    public Notification getCommentNotification(long postId, long postAccountId, long authenticatedAccountId){
        String sql = "select * from notifications where post_id = ? and notification_account_id = ? and authenticated_account_id = ? and commented = true";
        Notification notification = null;
        try{
            notification = jdbcTemplate.queryForObject(sql, new Object[] { postId, postAccountId, authenticatedAccountId  },
                    new BeanPropertyRowMapper<Notification>(Notification.class));
        }catch(Exception e){
            // log.error("error getting comment notification: " + e.getMessage());
        }
        return notification;
    }

    public boolean delete(long id) {
        String sql = "delete from notifications where id = ?";
        jdbcTemplate.update(sql, new Object[] { id });
        return true;
    }

}
