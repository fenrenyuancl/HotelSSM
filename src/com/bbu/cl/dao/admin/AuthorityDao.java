package com.bbu.cl.dao.admin;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bbu.cl.entity.admin.Authority;

/**
 * 权限管理dao
 * @author 疯人愿
 *
 */
@Repository
public interface AuthorityDao {
	//添加权限
	public int add(Authority authority);
	//添加roleid删除
	public int deleteByRoleId(Long roleId);
	//通过roleid获取权限列表
	public List<Authority> findListByRoleId(Long roleId);
}
