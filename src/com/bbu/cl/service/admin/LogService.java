package com.bbu.cl.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.admin.Log;

/**
 * 日志service接口
 * @author 疯人愿
 *
 */
@Service
public interface LogService {
	//添加日志
	public int add(Log log);
	//获取日志列表
	public List<Log> findList(Map<String, Object> queryMap);
	//获取日志数量
	public int getTotal(Map<String, Object> queryMap);
	//删除日志
	public int delete(String ids);
	//日志内容
	public int add(String content);
}
