package com.bbu.cl.serviceImpl.admin;

import java.util.Date;
import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbu.cl.dao.admin.FloorDao;
import com.bbu.cl.dao.admin.LogDao;
import com.bbu.cl.entity.admin.Floor;
import com.bbu.cl.entity.admin.Log;
import com.bbu.cl.service.admin.FloorService;
import com.bbu.cl.service.admin.LogService;

import sun.util.logging.resources.logging;
/**
 * 楼层Service实现类
 * @author 疯人愿
 *
 */
@Service
public class FloorServiceImpl implements FloorService {
	@Autowired 
	private FloorDao floorDao;
	@Override
	public int add(Floor floor) {
		// TODO Auto-generated method stub
		return floorDao.add(floor);
	}
	@Override
	public int edit(Floor floor) {
		// TODO Auto-generated method stub
		return floorDao.edit(floor);
	}
	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return floorDao.delete(id);
	}
	@Override
	public List<Floor> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return floorDao.findList(queryMap);
	}
	@Override
	public List<Floor> findAll() {
		// TODO Auto-generated method stub
		return floorDao.findAll();
	}
	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return floorDao.getTotal(queryMap);
	}
}
