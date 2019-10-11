package com.bbu.cl.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.admin.Floor;

/**
 * 楼层service接口
 * @author 疯人愿
 *
 */
@Service
public interface FloorService {
	//添加楼层
	public int add(Floor floor);
	//修改楼层
	public int edit(Floor floor);
	//删除楼层
	public int delete(Long id);
	//获取楼层列表
	public List<Floor> findList(Map<String, Object> queryMap);
	//获取所有楼层信息
	public List<Floor> findAll();
	//获取楼层数量
	public Integer getTotal(Map<String, Object> queryMap);
}
