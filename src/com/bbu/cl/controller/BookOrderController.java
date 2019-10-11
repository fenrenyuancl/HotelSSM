package com.bbu.cl.controller;

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
import com.bbu.cl.page.admin.Page;
import com.bbu.cl.service.AccountService;
import com.bbu.cl.service.BookOrderService;
import com.bbu.cl.service.RoomTypeService;
/**
 * 预定订单管理后台控制器
 * @author 疯人愿
 *
 */
@Controller
@RequestMapping("/admin/book_order")
public class BookOrderController {
	// 引入Service
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private BookOrderService bookOrderService;
	/**
	 * 获取预定订单页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	private ModelAndView List(ModelAndView model) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		model.addObject("accountList", accountService.findList(queryMap));
		model.addObject("roomTypeList", roomTypeService.findList(queryMap));
		model.setViewName("/book_order/list");
		return model;
	}

	/**
	 * 获取预定订单列表
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
			@RequestParam(name = "accountId", defaultValue = "") Long accountId,
			@RequestParam(name = "roomTypeId",  defaultValue = "") Long roomTypeId,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "idCard",defaultValue = "") String idCard,
			@RequestParam(name = "phoneNum",defaultValue = "") String phoneNum,
			@RequestParam(name = "status", required = false) Integer status) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		queryMap.put("accountId", accountId);
		queryMap.put("roomTypeId", roomTypeId);
		queryMap.put("name", name);
		queryMap.put("idCard", idCard);
		queryMap.put("phoneNum", phoneNum);
		queryMap.put("status", status);
		
		ret.put("rows", bookOrderService.findList(queryMap));
		ret.put("total", bookOrderService.getTotal(queryMap));
		return ret;
	}

	/**
	 * 添加预定订单
	 * 
	 * @param bookOrder
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(BookOrder bookOrder) {
		Map<String, String> ret = new HashMap<String, String>();
		if (bookOrder == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的预定订单信息！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getAccountId())) {
			ret.put("type", "error");
			ret.put("msg", "客户不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getRoomTypeId())) {
			ret.put("type", "error");
			ret.put("msg", "房间类型不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getName())) {
			ret.put("type", "error");
			ret.put("msg", "订单名不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getIdCard())) {
			ret.put("type", "error");
			ret.put("msg", "身份证号不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getPhoneNum())) {
			ret.put("type", "error");
			ret.put("msg", "手机号不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getArriveDate())) {
			ret.put("type", "error");
			ret.put("msg", "到达时间不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getLeaveDate())) {
			ret.put("type", "error");
			ret.put("msg", "离店时间不能为空！");
			return ret;
		}
		bookOrder.setCreateTime(new Date());
		if (bookOrderService.add(bookOrder) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员！");
			return ret;
		}
		RoomType roomType = roomTypeService.find(bookOrder.getRoomTypeId());
		//预定成功后去修改该房型的数量
		if (roomType != null) {
			roomType.setBookNum(roomType.getBookNum()  + 1);
			roomType.setAvilableNum(roomType.getAvilableNum() -1);
			roomTypeService.updateNum(roomType);
			//如果可用的房间数为0，则设置该房型已满
			if (roomType.getAvilableNum() == 0) {
				roomType.setStatus(0);
				roomTypeService.edit(roomType);
			}
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功！");
		return ret;
	}

	/**
	 * 修改预定订单
	 * 
	 * @param bookOrder
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(BookOrder bookOrder) {
		Map<String, String> ret = new HashMap<String, String>();
		if (bookOrder == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的预定订单信息！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getAccountId())) {
			ret.put("type", "error");
			ret.put("msg", "客户不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getRoomTypeId())) {
			ret.put("type", "error");
			ret.put("msg", "房间类型不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getName())) {
			ret.put("type", "error");
			ret.put("msg", "订单名不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getIdCard())) {
			ret.put("type", "error");
			ret.put("msg", "身份证号不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getPhoneNum())) {
			ret.put("type", "error");
			ret.put("msg", "手机号不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getArriveDate())) {
			ret.put("type", "error");
			ret.put("msg", "到达时间不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(bookOrder.getLeaveDate())) {
			ret.put("type", "error");
			ret.put("msg", "离店时间不能为空！");
			return ret;
		}
		BookOrder existBookOrder = bookOrderService.find(bookOrder.getId());
		if(existBookOrder == null){
			ret.put("type", "error");
			ret.put("msg", "请选择正确的数据进行编辑!");
			return ret;
		}
		if (bookOrderService.edit(bookOrder) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "修改失败，请联系管理员！");
			return ret;
		}
		//判断房型是否发生变化
		if(existBookOrder.getRoomTypeId().longValue() != bookOrder.getRoomTypeId().longValue()){
			//房型发生了变化
			//首先恢复原来房型的预定数及可用数
			RoomType oldRoomType = roomTypeService.find(existBookOrder.getRoomTypeId());
				oldRoomType.setAvilableNum(oldRoomType.getAvilableNum() + 1);
				oldRoomType.setBookNum(oldRoomType.getBookNum() - 1);
				roomTypeService.updateNum(oldRoomType);
				if(oldRoomType.getStatus() == 0){
					//旧的房间原来是满房，现在不满房的话，恢复状态
					oldRoomType.setStatus(1);
					roomTypeService.edit(oldRoomType);
					/*if(oldRoomType.getAvilableNum() > 0){
						//设置成状态可用
					}*/
				}
				//修改新的房型的可用数和预定数
				RoomType newRoomType = roomTypeService.find(bookOrder.getRoomTypeId());
				newRoomType.setAvilableNum(newRoomType.getAvilableNum() - 1);
				newRoomType.setBookNum(newRoomType.getBookNum() + 1);
				roomTypeService.updateNum(newRoomType);
				if(newRoomType.getAvilableNum() <= 0){
					//没有可用房间数
					newRoomType.setStatus(0);//设置成满房
					roomTypeService.edit(newRoomType);
				}
			}
		ret.put("type", "success");
		ret.put("msg", "修改成功！");
		return ret;
	}
}
