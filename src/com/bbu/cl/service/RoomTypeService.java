package com.bbu.cl.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.RoomType;

/**
 * 房型service
 * @author 疯人愿
 *
 */
@Service
public interface RoomTypeService {
	//添加房型
	public int add(RoomType roomType);
	//修改房型
	public int edit(RoomType roomType);
	//删除房型
	public int delete(Long id);
	//获取房型列表
	public List<RoomType> findList(Map<String, Object> queryMap);
	//获取所有房型信息
	public List<RoomType> findAll();
	//获取房型数量
	public int getTotal(Map<String, Object> queryMap);
	//获取单个房型信息
	public RoomType find(Long id);
	//房型预定数量编辑操作
	public int updateNum(RoomType roomType);
}
