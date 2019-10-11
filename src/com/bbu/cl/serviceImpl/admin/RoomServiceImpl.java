package com.bbu.cl.serviceImpl.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbu.cl.dao.admin.RoomDao;
import com.bbu.cl.entity.admin.Room;
import com.bbu.cl.service.admin.RoomService;
/**
 * 房型Service实现类
 * @author 疯人愿
 *
 */
@Service
public class RoomServiceImpl implements RoomService {
	@Autowired
	private RoomDao roomDao;

	@Override
	public int add(Room room) {
		// TODO Auto-generated method stub
		return roomDao.add(room);
	}

	@Override
	public int edit(Room room) {
		// TODO Auto-generated method stub
		return roomDao.edit(room);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return roomDao.delete(id);
	}

	@Override
	public List<Room> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return roomDao.findList(queryMap);
	}

	@Override
	public List<Room> findAll() {
		// TODO Auto-generated method stub
		return roomDao.findAll();
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return roomDao.getTotal(queryMap);
	}

	@Override
	public Room find(Long id) {
		// TODO Auto-generated method stub
		return roomDao.find(id);
	}

	@Override
	public Room findBySn(String sn) {
		// TODO Auto-generated method stub
		return roomDao.findBySn(sn);
	}

}
