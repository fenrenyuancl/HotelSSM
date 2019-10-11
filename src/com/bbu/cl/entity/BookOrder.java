package com.bbu.cl.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 预定订单实体类
 * @author 疯人愿
 *
 */
@Component
public class BookOrder {
	private Long id;					//预定订单id
	private Long accountId;				//用户id
	private Long roomTypeId;			//所属房型
	private String name;				//预定订单姓名
	private String idCard;				//身份证号
	private String phoneNum;			//手机号码
	private Date createTime;			//预定日期
	private String arriveDate;			//入住日期
	private String leaveDate;			//离店日期
	private int status;					//状态 0：预定中，1：已入住，2：订单已完成
	private String remark;				//备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Long getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Long roomTypeId) {
		this.roomTypeId = roomTypeId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
