package com.bbu.cl.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bbu.cl.entity.admin.Log;

/**
 * 日志管理dao
 * @author 疯人愿
 *
 */
@Repository
public interface LogDao {
	//添加日志
	public int add(Log log);
	//获取日志列表
	public List<Log> findList(Map<String, Object> queryMap);
	//获取日志数量
	public int getTotal(Map<String, Object> queryMap);
	//删除日志
	public int delete(String ids);
}
