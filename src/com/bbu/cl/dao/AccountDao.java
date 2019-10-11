package com.bbu.cl.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bbu.cl.entity.Account;

/**
 * 客户dao
 * @author 疯人愿
 *
 */
@Repository
public interface AccountDao {
	//添加客户
	public int add(Account account);
	//修改客户
	public int edit(Account account);
	//删除客户
	public int delete(Long id);
	//获取客户列表
	public List<Account> findList(Map<String, Object> queryMap);
	//获取所有客户信息
	public List<Account> findAll();
	//获取客户数量
	public Integer getTotal(Map<String, Object> queryMap);
	//获取单个客户信息
	public Account find(Long id);
	//根据客户登录名获取单个客户信息
	public Account findByUsername(String username);
}
