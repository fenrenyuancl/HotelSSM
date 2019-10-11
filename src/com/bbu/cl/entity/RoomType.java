package com.bbu.cl.entity;

import org.springframework.stereotype.Component;

/**
 * 房间类型实体类
 * @author 疯人愿
 *
 */
@Component
public class RoomType {
	private Long id;				//房间类型id
	private String name;			//房间名称
	private Float price;			//房间价格
	private Integer liveNum;		//可住人数
	private Integer bedNum;			//床位数
	private Integer roomNum;		//房间数
	private Integer avilableNum;	//可住或可预订房间数
	private Integer bookNum;		//预定数
	private Integer livedNum;		//已经入住数
	private int status;				//房间状态  0房型已满，1可预订可入住
	private String photo;			//房型图片
	private String remark;			//房型备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getLiveNum() {
		return liveNum;
	}
	public void setLiveNum(Integer liveNum) {
		this.liveNum = liveNum;
	}
	public Integer getBedNum() {
		return bedNum;
	}
	public void setBedNum(Integer bedNum) {
		this.bedNum = bedNum;
	}
	public Integer getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}
	public Integer getAvilableNum() {
		return avilableNum;
	}
	public void setAvilableNum(Integer avilableNum) {
		this.avilableNum = avilableNum;
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
	public Integer getBookNum() {
		return bookNum;
	}
	public void setBookNum(Integer bookNum) {
		this.bookNum = bookNum;
	}
	public Integer getLivedNum() {
		return livedNum;
	}
	public void setLivedNum(Integer livedNum) {
		this.livedNum = livedNum;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
