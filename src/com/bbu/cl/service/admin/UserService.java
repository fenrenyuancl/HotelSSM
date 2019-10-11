package com.bbu.cl.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.admin.User;

/**
 * User用户Service
 * @author 疯人愿
 *
 */
@Service
public interface UserService {
	//根据用户名查询用户
	public User findByUsername(String username); 
	//添加用户
	public int add(User user);
	//修改用户
	public int edit(User user);
	//删除用户
	public int delete(String ids);
	//获取列表
	public List<User> findList(Map<String, Object> queryMap);
	//获取数量
	public int getTotal(Map<String, Object> queryMap);
	//修改密码
	public int editPassword(User user);
}
