package com.bbu.cl.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.admin.Role;

/**
 * 角色RoleService
 * @author 疯人愿
 *
 */
@Service
public interface RoleService {
	//角色添加操作
	public int add(Role role);
	//角色修改操作
	public int edit(Role  role);
	//角色删除操作
	public int delete(Long id);
	//获取角色列表数据操作
	public List<RoleService> findList(Map<String, Object> queryMap);
	//获取数量
	public int getTotal(Map<String, Object> queryMap);
	//根据id查询角色
	public Role find(Long id);
}
