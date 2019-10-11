package com.bbu.cl.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.admin.Authority;

/**
 * 权限service接口
 * @author 疯人愿
 *
 */
@Service
public interface AuthorityService {
	//添加权限
	public int add(Authority authority);
	//添加roleid删除
	public int deleteByRoleId(Long roleId);
	//通过roleid获取权限列表
	public List<Authority> findListByRoleId(Long roleId);
}
