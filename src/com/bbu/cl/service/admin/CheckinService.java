package com.bbu.cl.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.admin.Checkin;

/**
 * 入住信息service
 * @author 疯人愿
 *
 */
@Service
public interface CheckinService {
	//添加入住信息
	public int add(Checkin checkin);
	//修改入住信息
	public int edit(Checkin checkin);
	//删除入住信息
	public int delect(Long id);
	//获取入住信息列表
	public List<Checkin> findList(Map<String, Object> queryMap);
	//获取所有入住信息信息
	public List<Checkin> findAll();
	//获取入住信息数量
	public Integer getTotal(Map<String, Object> queryMap);
	//获取单个入住信息信息
	public Checkin find(Long id);
	//根据月份统计
	public List<Map> getStatsByMonth();
	//根据日统计
	public List<Map> getStatsByDay();
}
