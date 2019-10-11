package com.bbu.cl.entity.admin;

import org.springframework.stereotype.Component;

/**
 * 权限实体
 * @author 疯人愿
 *
 */
@Component
public class Authority {
	private Long id;				//权限id
	private Long roleId;			//角色id
	private Long menuId;			//菜单id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}
