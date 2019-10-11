package com.bbu.cl.entity.admin;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 入住实体类
 * @author 疯人愿
 *
 */
@Component
public class Checkin {
	private Long id;					//入住id
	private Long roomTypeId;			//所属房型
	private Long roomId;				//所属房间
	private Long bookOrderId;			//预定订单id，可为空
	private String name;				//入住人姓名
	private Float checkinPrice;			//入住价格
	private String idCard;				//入住人身份证号
	private String phoneNum;			//手机号码
	private Date createTime;			//创建日期
	private String arriveDate;			//入住日期
	private String leaveDate;			//离店日期
	private int status;					//状态 0：入住中，1：订单已完成
	private String remark;				//备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getCheckinPrice() {
		return checkinPrice;
	}
	public void setCheckinPrice(Float checkinPrice) {
		this.checkinPrice = checkinPrice;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getBookOrderId() {
		return bookOrderId;
	}
	public void setBookOrderId(Long bookOrderId) {
		this.bookOrderId = bookOrderId;
	}
	
}
