package xyz.ioc.dao;

import xyz.ioc.model.Resource;
import xyz.ioc.model.ResourceLike;
import xyz.ioc.model.ResourceShare;

import java.util.List;

public interface ResourceDao {

    public Resource get(String uri);

    public boolean save(Resource resource);

    public boolean like(ResourceLike resourceLike);

    public boolean liked(ResourceLike resourceLike);

    public boolean share(ResourceShare resourceShare);

    public long likesCount(long resourceId);

    public long sharesCount(long resourceId);

}
