package com.bbu.cl.controller.admin;

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

import com.bbu.cl.entity.admin.Floor;
import com.bbu.cl.page.admin.Page;
import com.bbu.cl.service.admin.FloorService;
/**
 * 楼层管理控制器
 * @author 疯人愿
 *
 */
@Controller
@RequestMapping("/admin/floor")
public class FloorController {
	//引入Service
	@Autowired
	private FloorService floorService;
	
	/**
	 * 获取楼层页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	private ModelAndView List(ModelAndView model) {
		model.setViewName("/floor/list");
		return model;
	}
	/**
	 * 获取楼层列表
	 * @param page
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> geFloorList(Page page,
			@RequestParam(name="name",required=false,defaultValue="") String name
			) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		queryMap.put("name", name);
		List<Floor> findList = floorService.findList(queryMap);
		ret.put("rows", findList);
		ret.put("total", floorService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 添加楼层
	 * @param floor
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Floor floor){
		Map<String, String> ret = new HashMap<String, String>();
		if (floor == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的楼层信息！");
			return ret;
		}
		if (StringUtils.isEmpty(floor.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写楼层名称！");
			return ret;
		}
		if (floorService.add(floor)<=0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功！");
		return ret;
	}
	/**
	 * 修改楼层
	 * @param floor
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Floor floor){
		Map<String, String> ret = new HashMap<String, String>();
		if (floor == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的楼层信息！");
			return ret;
		}
		if (StringUtils.isEmpty(floor.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写楼层名称！");
			return ret;
		}
		if (floorService.edit(floor)<=0) {
			ret.put("type", "error");
			ret.put("msg", "修改失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "修改成功！");
		return ret;
	}
	/**
	 * 楼层删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(Long id){
		Map<String, String> ret = new HashMap<String, String>();
		if (id == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择要删除的楼层信息！");
			return ret;
		}
		/*List<Floor> findChildrenList = floorService.findChildrenList(id);
		if (findChildrenList != null && findChildrenList.size()>0) {
			//表示该分类下有子分类，不能删除
			ret.put("type", "error");
			ret.put("msg", "该分类下有子分类，不能删除！");
			return ret;
		}
*/		try {
			if (floorService.delete(id)<=0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员！");
				return ret;
				}
			} catch (Exception e) {
				// TODO: handle exception
				ret.put("type", "error");
				ret.put("msg", "该楼层下存在房间信息，请先删除该楼层下的所有房间信息！");
				return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功！");
		return ret;
	}
}
