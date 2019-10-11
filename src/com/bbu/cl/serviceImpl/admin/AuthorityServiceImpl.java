package com.bbu.cl.serviceImpl.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbu.cl.dao.admin.AuthorityDao;
import com.bbu.cl.entity.admin.Authority;
import com.bbu.cl.service.admin.AuthorityService;
/**
 * 权限service实现类
 * @author 疯人愿
 *
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityDao authorityDao;
	@Override
	public int add(Authority authority) {
		// TODO Auto-generated method stub
		return authorityDao.add(authority);
	}
	@Override
	public int deleteByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return authorityDao.deleteByRoleId(roleId);
	}
	@Override
	public List<Authority> findListByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return authorityDao.findListByRoleId(roleId);
	}
}
