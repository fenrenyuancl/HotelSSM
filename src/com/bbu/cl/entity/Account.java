package com.bbu.cl.entity;

import org.springframework.stereotype.Component;

/**
 * 客户实体类
 * @author 疯人愿
 *
 */
@Component
public class Account {
	private Long id;					//客户id
	private String username;			//登录名
	private String password;			//客户密码
	private String name;				//客户姓名
	private String idCard;				//身份证号
	private String phoneNum;			//手机号码
	private String address;				//地址
	private int status;					//状态，0：可用，1：冻结
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
