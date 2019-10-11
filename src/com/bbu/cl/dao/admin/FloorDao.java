package com.bbu.cl.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bbu.cl.entity.admin.Floor;
import com.bbu.cl.entity.admin.Log;

/**
 * 楼层管理dao
 * @author 疯人愿
 *
 */
@Repository
public interface FloorDao {
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
