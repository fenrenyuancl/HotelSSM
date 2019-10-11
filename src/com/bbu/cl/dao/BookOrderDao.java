package com.bbu.cl.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bbu.cl.entity.BookOrder;

/**
 * 预定订单dao
 * 
 * @author 疯人愿
 *
 */
@Repository
public interface BookOrderDao {
	// 添加预定订单
	public int add(BookOrder bookOrder);

	// 修改预定订单
	public int edit(BookOrder bookOrder);
	//删除预定订单（只有当在入住页面导入预定订单入住时才会删除）
	//	public int delete(Long id);
	public int delete(Long id);
	// 获取预定订单列表
	public List<BookOrder> findList(Map<String, Object> queryMap);

	// 获取所有预定订单信息
	public List<BookOrder> findAll();

	// 获取预定订单数量
	public Integer getTotal(Map<String, Object> queryMap);

	// 获取单个预定订单信息
	public BookOrder find(Long id);
	
}
