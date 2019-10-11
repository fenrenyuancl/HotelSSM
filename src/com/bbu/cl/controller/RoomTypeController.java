package com.bbu.cl.controller;

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

import com.bbu.cl.entity.RoomType;
import com.bbu.cl.entity.admin.Floor;
import com.bbu.cl.page.admin.Page;
import com.bbu.cl.service.RoomTypeService;
import com.bbu.cl.service.admin.FloorService;
/**
 * 房间类型管理后台控制器
 * @author 疯人愿
 *
 */
@Controller
@RequestMapping("/admin/room_type")
public class RoomTypeController {
	// 引入Service
	@Autowired
	private RoomTypeService roomTypeService;

	/**
	 * 获取房型页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	private ModelAndView List(ModelAndView model) {
		model.setViewName("/room_type/list");
		return model;
	}

	/**
	 * 获取房型列表
	 * 
	 * @param page
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> List(Page page,
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(name = "status", required = false) Integer status) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		queryMap.put("name", name);
		queryMap.put("status", status);
		List<RoomType> findList = roomTypeService.findList(queryMap);
		ret.put("rows", findList);
		ret.put("total", roomTypeService.getTotal(queryMap));
		return ret;
	}

	/**
	 * 添加房型
	 * 
	 * @param roomType
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(RoomType roomType) {
		Map<String, String> ret = new HashMap<String, String>();
		if (roomType == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的房型信息！");
			return ret;
		}
		if (StringUtils.isEmpty(roomType.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写房型名称！");
			return ret;
		}
		roomType.setAvilableNum(roomType.getRoomNum());//默认房间数等于可用房间数
		roomType.setBookNum(0);//默认已经预定房间数为0
		roomType.setLivedNum(0);//默认已经入住房间数为0
		if (roomTypeService.add(roomType) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功！");
		return ret;
	}

	/**
	 * 修改房型
	 * 
	 * @param roomType
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(RoomType roomType) {
		Map<String, String> ret = new HashMap<String, String>();
		if (roomType == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的房型信息！");
			return ret;
		}
		if (StringUtils.isEmpty(roomType.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写房型名称！");
			return ret;
		}
		RoomType existRoomType = roomTypeService.find(roomType.getId());
		if (existRoomType == null) {
			ret.put("type", "error");
			ret.put("msg", "未找到该数据！");
			return ret;
		}
		int offset = roomType.getRoomNum() - existRoomType.getRoomNum();//offset是修改后的房间数与之前添加的房间数之间的差值
		roomType.setAvilableNum(existRoomType.getAvilableNum() + offset);//修改后的可预定房间为修改后的可预定房间数+差值
		if (roomType.getAvilableNum() <= 0) {
			//如果修改后的可入住房间数小于-1，将可入住房间数强制为0，表示房型已满
			roomType.setAvilableNum(0);
			roomType.setStatus(0);//房型已满
			if (roomType.getAvilableNum() + existRoomType.getLivedNum() + existRoomType.getBookNum()  > existRoomType.getRoomNum()) {
				ret.put("type", "error");
				ret.put("msg", "房间数设置不合理！");
				return ret;
			}
		}
		if (roomTypeService.edit(roomType) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "修改失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "修改成功！");
		return ret;
	}

	/**
	 * 房型删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(Long id) {
		Map<String, String> ret = new HashMap<String, String>();
		if (id == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择要删除的房型信息！");
			return ret;
		}
		/*
		 * List<Floor> findChildrenList = floorService.findChildrenList(id); if
		 * (findChildrenList != null && findChildrenList.size()>0) { //表示该分类下有子分类，不能删除
		 * ret.put("type", "error"); ret.put("msg", "该分类下有子分类，不能删除！"); return ret; }
		 */ try {
			if (roomTypeService.delete(id) <= 0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员！");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该房型下存在房间信息，请先删除该房型下的所有房间信息！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功！");
		return ret;
	}
}
