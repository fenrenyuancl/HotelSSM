package com.bbu.cl.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.admin.Menu;

/**
 * 菜单管理service
 * @author 疯人愿
 *
 */
@Service
public interface MenuService {
	//菜单添加方法
	public int add(Menu menu);
	//菜单信息模糊分页搜索查询
	public List<Menu> findList(Map<String, Object> queryMap);
	//获取顶级菜单信息
	public List<Menu> findTopList();
	//获取子菜单信息
	public List<Menu> findChildrenList(Long parentId);
	//根据菜单id获取菜单信息
	public List<Menu> findListByIds(String ids);
	//获取数量
	public int getTotal();
	//菜单修改方法
	public int edit(Menu menu);
	//菜单删除方法
	public int delete(Long id);
}
