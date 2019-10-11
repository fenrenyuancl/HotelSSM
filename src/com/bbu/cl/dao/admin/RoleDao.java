package com.bbu.cl.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bbu.cl.entity.admin.Role;
import com.bbu.cl.service.admin.RoleService;
/**
 * 角色Role dao
 * @author 疯人愿
 *
 */
@Repository
public interface RoleDao {
	//角色添加操作
	public int add(Role role);
	//角色修改操作
	public int edit(Role role);
	//角色删除操作
	public int delete(Long id);
	//获取角色列表数据操作
	public List<RoleService> findList(Map<String, Object> queryMap);
	//获取数量
	public int getTotal(Map<String, Object> queryMap);
	//根据id查询角色
	public Role find(Long id);
}
