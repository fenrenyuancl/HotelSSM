package com.bbu.cl.serviceImpl.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbu.cl.dao.admin.RoleDao;
import com.bbu.cl.entity.admin.Role;
import com.bbu.cl.service.admin.RoleService;
/**
 * 角色Role的实现类
 * @author 疯人愿
 *
 */
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public int add(Role role) {
		// TODO Auto-generated method stub
		return roleDao.add(role);
	}

	@Override
	public int edit(Role role) {
		// TODO Auto-generated method stub
		return roleDao.edit(role);
	}
	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return roleDao.delete(id);
	}

	@Override
	public List<RoleService> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return roleDao.findList(queryMap);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return roleDao.getTotal(queryMap);
	}

	@Override
	public Role find(Long id) {
		// TODO Auto-generated method stub
		return roleDao.find(id);
	}
}
