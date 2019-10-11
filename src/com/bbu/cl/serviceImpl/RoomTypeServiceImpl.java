package com.bbu.cl.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbu.cl.dao.RoomTypeDao;
import com.bbu.cl.entity.RoomType;
import com.bbu.cl.service.RoomTypeService;
/**
 * 房型Service实现类
 * @author 疯人愿
 *
 */
@Service
public class RoomTypeServiceImpl implements RoomTypeService {
	@Autowired
	private RoomTypeDao roomTypeDao;

	@Override
	public int add(RoomType roomType) {
		// TODO Auto-generated method stub
		return roomTypeDao.add(roomType);
	}

	@Override
	public int edit(RoomType roomType) {
		// TODO Auto-generated method stub
		return roomTypeDao.edit(roomType);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return roomTypeDao.delete(id);
	}

	@Override
	public List<RoomType> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return roomTypeDao.findList(queryMap);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return roomTypeDao.getTotal(queryMap);
	}

	@Override
	public List<RoomType> findAll() {
		// TODO Auto-generated method stub
		return roomTypeDao.findAll();
	}

	@Override
	public RoomType find(Long id) {
		// TODO Auto-generated method stub
		return roomTypeDao.find(id);
	}

	@Override
	public int updateNum(RoomType roomType) {
		// TODO Auto-generated method stub
		return roomTypeDao.updateNum(roomType);
	}

	
}
