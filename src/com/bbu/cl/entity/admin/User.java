package com.bbu.cl.entity.admin;

import org.springframework.stereotype.Component;

/**
 * 用户实体类
 * @author 疯人愿
 *
 */

public class User {
	private Long id;				//用户id，设置自增
	private String username;		//用户名、登录名
	private String password;		//登录密码
	private Long roleId;  			//所属角色id
	private String photo;			//头像的照片地址
	private Integer sex;			//用户性别 0代表未知，1表示男，2表示女
	private Integer age;			//用户年龄
	private String address;			//家庭地址
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
