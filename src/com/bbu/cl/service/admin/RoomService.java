package com.bbu.cl.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.RoomType;
import com.bbu.cl.entity.admin.Room;

/**
 * 房间service
 * @author 疯人愿
 *
 */
@Service
public interface RoomService {
	//添加房间
	public int add(Room room);
	//修改房间
	public int edit(Room room);
	//删除房间
	public int delete(Long id);
	//获取房间列表
	public List<Room> findList(Map<String, Object> queryMap);
	//获取所有房间信息
	public List<Room> findAll();
	//获取房间数量
	public int getTotal(Map<String, Object> queryMap);
	//获取单个房间信息
	public Room find(Long id);
	//根据房间编号获取单个房间信息
	public Room findBySn(String sn);
}
