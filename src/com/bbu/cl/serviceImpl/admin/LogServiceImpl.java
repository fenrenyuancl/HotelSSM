package com.bbu.cl.serviceImpl.admin;

import java.util.Date;
import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbu.cl.dao.admin.LogDao;
import com.bbu.cl.entity.admin.Log;
import com.bbu.cl.service.admin.LogService;

import sun.util.logging.resources.logging;
/**
 * 日志Service实现类
 * @author 疯人愿
 *
 */
@Service
public class LogServiceImpl implements LogService {
	@Autowired 
	private LogDao logDao;
	@Override
	public int add(Log log) {
		// TODO Auto-generated method stub
		return logDao.add(log);
	}
	@Override
	public List<Log> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return logDao.findList(queryMap);
	}
	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return logDao.getTotal(queryMap);
	}
	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return logDao.delete(ids);
	}
	@Override
	public int add(String content) {
		Log log = new Log();
		log.setContent(content);
		log.setCreateTime(new Date());
		return logDao.add(log);
	}
}
