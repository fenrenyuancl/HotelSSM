package com.bbu.cl.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bbu.cl.entity.BookOrder;
import com.bbu.cl.entity.RoomType;
import com.bbu.cl.entity.admin.Checkin;
import com.bbu.cl.entity.admin.Room;
import com.bbu.cl.page.admin.Page;
import com.bbu.cl.service.BookOrderService;
import com.bbu.cl.service.RoomTypeService;
import com.bbu.cl.service.admin.CheckinService;
import com.bbu.cl.service.admin.RoomService;
/**
 * 入住订单管理后台控制器
 * @author 疯人愿
 *
 */
@Controller
@RequestMapping("/admin/checkin")
public class CheckinController {
	// 引入Service
	@Autowired
	private RoomService roomService;
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private CheckinService checkinService;
	@Autowired
	private BookOrderService bookOrderService;
	/**
	 * 获取入住订单页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	private ModelAndView List(ModelAndView model) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		model.addObject("roomList", roomService.findList(queryMap));
		model.addObject("roomTypeList", roomTypeService.findList(queryMap));
		model.setViewName("/checkin/list");
		return model;
	}

	/**
	 * 获取入住订单列表
	 * @param page
	 * @param username
	 * @param name
	 * @param idCard
	 * @param phoneNum
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> List(Page page,
			@RequestParam(name = "roomTypeId",  defaultValue = "") Long roomTypeId,
			@RequestParam(name = "roomId", defaultValue = "") Long roomId,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "idCard",defaultValue = "") String idCard,
			@RequestParam(name = "phoneNum",defaultValue = "") String phoneNum,
			@RequestParam(name = "status", required = false) Integer status) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		queryMap.put("roomTypeId", roomTypeId);
		queryMap.put("roomId", roomId);
		queryMap.put("name", name);
		queryMap.put("idCard", idCard);
		queryMap.put("phoneNum", phoneNum);
		queryMap.put("status", status);
		
		ret.put("rows", checkinService.findList(queryMap));
		ret.put("total", checkinService.getTotal(queryMap));
		return ret;
	}

	/**
	 * 添加入住订单
	 * 
	 * @param checkin
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Checkin checkin,
			@RequestParam(name="bookOrderId",required=false)Long bookOrderId
			) {
		Map<String, String> ret = new HashMap<String, String>();
		if (checkin == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的入住订单信息！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getRoomId())) {
			ret.put("type", "error");
			ret.put("msg", "客间不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getRoomTypeId())) {
			ret.put("type", "error");
			ret.put("msg", "房间类型不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getName())) {
			ret.put("type", "error");
			ret.put("msg", "订单名不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getIdCard())) {
			ret.put("type", "error");
			ret.put("msg", "身份证号不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getPhoneNum())) {
			ret.put("type", "error");
			ret.put("msg", "手机号不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getArriveDate())) {
			ret.put("type", "error");
			ret.put("msg", "到达时间不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getLeaveDate())) {
			ret.put("type", "error");
			ret.put("msg", "离店时间不能为空！");
			return ret;
		}
		checkin.setCreateTime(new Date());
		if (checkinService.add(checkin) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员！");
			return ret;
		} 
		RoomType roomType = roomTypeService.find(checkin.getRoomTypeId());
		Room room = roomService.find(checkin.getRoomId());
		
		//判断是否从预定列表中来的订单
		if (bookOrderId == null) {
			//是从预定列表中来的订单
			roomType.setBookNum(roomType.getBookNum()  - 1);//预定数-1
			roomType.setLivedNum(roomType.getLivedNum() + 1);//入住数+1
			roomTypeService.updateNum(roomType);
		}else {
			//不是从从预定列表中来的订单，此时需要判断房间是否已满
			if (roomType.getAvilableNum()>0) {
				//房间未满，还可以继续入住
				roomType.setAvilableNum(roomType.getAvilableNum() -1);//可预定数-1
				roomType.setLivedNum(roomType.getLivedNum() + 1);//入住数+1
				roomTypeService.updateNum(roomType);
				//删除预定订单页面中的该订单
				bookOrderService.delete(bookOrderId);
				//再判断房间是否已满
				if (roomType.getAvilableNum()<=0) {
					//房间数已满
					roomType.setStatus(0);//设置房型状态为已满
					roomTypeService.edit(roomType);
				}
			}
		}
		//如果入住的id等于预定订单的id则表示是从预定订单--->入住
		if (checkin.getBookOrderId() == bookOrderId) {
			room.setStatus(1);//房间状态设置为已入住
			roomService.edit(room);
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功！");
		return ret;
	}

	/**
	 * 修改入住订单
	 * @param checkin
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Checkin checkin,
			@RequestParam(name="bookOrderId",required=false)Long bookOrderId
			) {
		Map<String, String> ret = new HashMap<String, String>();
		if (checkin == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的入住订单信息！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getRoomId())) {
			ret.put("type", "error");
			ret.put("msg", "客间不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getRoomTypeId())) {
			ret.put("type", "error");
			ret.put("msg", "房间类型不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getName())) {
			ret.put("type", "error");
			ret.put("msg", "订单名不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getIdCard())) {
			ret.put("type", "error");
			ret.put("msg", "身份证号不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getPhoneNum())) {
			ret.put("type", "error");
			ret.put("msg", "手机号不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getArriveDate())) {
			ret.put("type", "error");
			ret.put("msg", "到达时间不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(checkin.getLeaveDate())) {
			ret.put("type", "error");
			ret.put("msg", "离店时间不能为空！");
			return ret;
		}
		//获得修改前入住订单
		Checkin existCheckin = checkinService.find(checkin.getId());
		if (existCheckin == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择正确的入住信息进行编辑！");
			return ret;
		}
		if (checkinService.edit(checkin) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "修改失败，请联系管理员！");
			return ret;
		}
		/*编辑成功后
		 *  1.判断房型是否发生变化
		 *  2.判断房间是否发生变化
		 * */
		//修改订单前的房型
		RoomType oldRoomType = roomTypeService.find(existCheckin.getRoomTypeId());
		//修改订单前的房间
		Room oldRoom = roomService.find(existCheckin.getRoomId());
		//修改订单后的房型
		RoomType newRoomType = roomTypeService.find(checkin.getRoomTypeId());
		//修改订单后的房间
		Room newRoom = roomService.find(checkin.getRoomId());
		//判断房型是否发生变化
		if (oldRoomType.getId().longValue() != newRoomType.getId().longValue()) {
			//房型发生变化
			oldRoomType.setLivedNum(oldRoomType.getLivedNum() -1);//原房型入住数-1
			oldRoomType.setAvilableNum(oldRoomType.getAvilableNum() +1);//原房型的可预定数+ 1
			oldRoom.setStatus(0);//修改以前的房间状态为可入住
			newRoomType.setAvilableNum(newRoomType.getAvilableNum() -1);//新房型的可预定数-1
			newRoomType.setLivedNum(newRoomType.getLivedNum() +1);//新房型的入住数+1
			newRoom.setStatus(1);//修改现在的房间状态为已入住
		}else {
			//房型没有发生变化,只需判断房间是否发生变化
			if (oldRoom.getId().longValue() != newRoom.getId().longValue()) {
				//房间发生变化
				oldRoom.setStatus(0);//修改以前的房间状态为可入住
				newRoom.setStatus(1);//修改现在的房间状态为已入住
			}
		}
		roomTypeService.updateNum(newRoomType);
		roomService.edit(oldRoom);
		roomTypeService.updateNum(oldRoomType);
		roomService.edit(newRoom);
		ret.put("type", "success");
		ret.put("msg", "修改成功！");
		return ret;
	}
	/**
	 * 退房操作
	 * @param checkId
	 * @return
	 */
	@RequestMapping(value="/checkout",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> checkout(Long checkId
			){
		Map<String, String> ret = new HashMap<String, String>();
		if(checkId == null){
			ret.put("type", "error");
			ret.put("msg", "请选择数据!");
			return ret;
		}
		Checkin checkin = checkinService.find(checkId);
		if(checkin == null){
			ret.put("type", "error");
			ret.put("msg", "请选择正确的数据!");
			return ret;
		}
		checkin.setStatus(1);//设置订单状态已完成
		if(checkinService.edit(checkin) <= 0){
			ret.put("type", "error");
			ret.put("msg", "退房失败，请联系管理员!");
			return ret;
		}
		//首先操作房间状态
		Room room = roomService.find(checkin.getRoomId());
		if(room != null){
			room.setStatus(2);//设置房间状态打扫中，打扫完成需要后台设置成可预订
			roomService.edit(room);
		}
		//其次修改房型可用数、入住数、状态
		RoomType roomType = roomTypeService.find(checkin.getRoomTypeId());
		if(roomType != null){
			roomType.setAvilableNum(roomType.getAvilableNum() + 1);
			roomType.setLivedNum(roomType.getLivedNum() - 1);
			roomType.setStatus(1);
			roomTypeService.updateNum(roomType);
			roomTypeService.edit(roomType);
		}
		//判断是否来自预定
		if(checkin.getBookOrderId() != null){
			BookOrder bookOrder = bookOrderService.find(checkin.getBookOrderId());
			bookOrder.setStatus(2);
			bookOrderService.edit(bookOrder);
			
		}
		ret.put("type", "success");
		ret.put("msg", "退房成功!");
		return ret;
	}
	/**
	 * 根据房间类型获取房间
	 * @param roomTypeId
	 * @return
	 */
	@RequestMapping(value="/load_room_list",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> load_room_list(Long roomTypeId){
		List<Map<String, Object>> retList = new ArrayList<Map<String,Object>>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("roomTypeId", roomTypeId);
		queryMap.put("status", 0);
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 999);
		List<Room> roomList = roomService.findList(queryMap);
		for(Room room:roomList){
			Map<String, Object> option = new HashMap<String, Object>();
			option.put("value", room.getId());
			option.put("text", room.getSn());
			retList.add(option);
		}
		return retList;
	}
}
