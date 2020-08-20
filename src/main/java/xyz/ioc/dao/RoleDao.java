package xyz.ioc.dao;

import java.util.List;
import xyz.ioc.model.Role;

public interface RoleDao {

	public int count();

	public Role get(int id);
	
	public Role find(String name);
	
	public void save(Role role);

	// public Role save(Role role);
	
}