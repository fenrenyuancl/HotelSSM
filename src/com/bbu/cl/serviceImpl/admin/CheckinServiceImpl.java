package com.bbu.cl.serviceImpl.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbu.cl.dao.BookOrderDao;
import com.bbu.cl.dao.admin.CheckinDao;
import com.bbu.cl.entity.BookOrder;
import com.bbu.cl.entity.admin.Checkin;
import com.bbu.cl.service.admin.CheckinService;
/**
 * 客户Service实现类
 * @author 疯人愿
 *
 */
@Service
public class CheckinServiceImpl implements CheckinService {
	@Autowired
	private CheckinDao checkinDao;

	@Override
	public int add(Checkin checkin) {
		// TODO Auto-generated method stub
		return checkinDao.add(checkin);
	}

	@Override
	public int edit(Checkin checkin) {
		// TODO Auto-generated method stub
		return checkinDao.edit(checkin);
	}

	@Override
	public List<Checkin> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return checkinDao.findList(queryMap);
	}

	@Override
	public List<Checkin> findAll() {
		// TODO Auto-generated method stub
		return checkinDao.findAll();
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return checkinDao.getTotal(queryMap);
	}

	@Override
	public Checkin find(Long id) {
		// TODO Auto-generated method stub
		return checkinDao.find(id);
	}

	@Override
	public int delect(Long id) {
		// TODO Auto-generated method stub
		return checkinDao.delect(id);
	}

	@Override
	public List<Map> getStatsByMonth() {
		// TODO Auto-generated method stub
		return checkinDao.getStatsByMonth();
	}

	@Override
	public List<Map> getStatsByDay() {
		// TODO Auto-generated method stub
		return checkinDao.getStatsByDay();
	}

	
}
