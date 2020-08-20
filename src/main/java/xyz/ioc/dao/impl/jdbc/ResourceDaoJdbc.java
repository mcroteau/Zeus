package xyz.ioc.dao.impl.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.ioc.dao.ResourceDao;
import xyz.ioc.model.*;

import java.util.List;

public class ResourceDaoJdbc implements ResourceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Resource get(String uri) {
        Resource resource = null;
        try{
            String sql = "select * from resources where uri = ?";
            resource = jdbcTemplate.queryForObject(sql, new Object[] { uri },
                    new BeanPropertyRowMapper<Resource>(Resource.class));

        }catch(EmptyResultDataAccessException e){}
        return resource;
    }

    @Override
    public boolean save(Resource resource) {
        String sql = "insert into resources ( uri, account_id, date_added ) values ( ?, ?, ? )";
        jdbcTemplate.update(sql, new Object[] {
            resource.getUri(), resource.getAccountId(), resource.getDateAdded()
        });
        return true;
    }

    @Override
    public boolean like(ResourceLike resourceLike){
        String sql = "insert into resource_likes (resource_id, account_id, date_liked) values ( ?, ?, ? )";
        jdbcTemplate.update(sql, new Object[] {
                resourceLike.getResourceId(), resourceLike.getAccountId(), resourceLike.getDateLiked()
        });
        return true;
    }


    @Override
    public boolean liked(ResourceLike resourceLike){
        String sql = "select * from resource_likes where resource_id = ? and account_id = ?";
        ResourceLike existingLike = null;
        try {
            existingLike = jdbcTemplate.queryForObject(sql, new Object[]{ resourceLike.getResourceId(), resourceLike.getAccountId() }, new BeanPropertyRowMapper<ResourceLike>(ResourceLike.class));
        }catch(Exception e){}

        if(existingLike != null) return true;
        return false;
    }

    @Override
    public boolean share(ResourceShare resourceShare){
        String sql = "insert into resource_shares (resource_id, account_id, post_id, date_shared, comment) values ( ?, ?, ?, ?, ? )";
        jdbcTemplate.update(sql, new Object[] {
                resourceShare.getResourceId(), resourceShare.getAccountId(), resourceShare.getPostId(), resourceShare.getDateShared(), resourceShare.getComment()
        });
        return true;
    }

    @Override
    public long likesCount(long resourceId) {
        String sql = "select count(*) from resource_likes where resource_id = ?";
        long count = jdbcTemplate.queryForObject(sql, new Object[]{ resourceId }, Long.class);
        System.out.println("likes >" + resourceId + " : " + count);
        return count;
    }

    @Override
    public long sharesCount(long resourceId) {
        String sql = "select count(*) from resource_shares where resource_id = ?";
        long count = jdbcTemplate.queryForObject(sql, new Object[]{ resourceId }, Long.class);
        return count;
    }

}
