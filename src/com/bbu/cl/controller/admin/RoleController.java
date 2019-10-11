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

import com.bbu.cl.entity.admin.Authority;
import com.bbu.cl.entity.admin.Menu;
import com.bbu.cl.entity.admin.Role;
import com.bbu.cl.page.admin.Page;
import com.bbu.cl.service.admin.AuthorityService;
import com.bbu.cl.service.admin.MenuService;
import com.bbu.cl.service.admin.RoleService;

/**
 * 角色Role控制器
 * @author 疯人愿
 *
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private MenuService menuService;
	/**
	 * 角色列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		model.addObject("roleList", roleService.findList(queryMap));
		model.setViewName("/role/list");
		return model;
	}
	/**
	 * 获取角色列表详情页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			Page page,
			@RequestParam(name="name",required=false,defaultValue="") String name
			) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		queryMap.put("name", name);
		ret.put("rows", roleService.findList(queryMap));
		ret.put("total", roleService.getTotal(queryMap));
		return ret;
	}
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Role role){
		Map<String, String> ret = new HashMap<String, String>();
		if (role == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的角色信息！");
			return ret;
		}
		if (StringUtils.isEmpty(role.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写角色名称！");
			return ret;
		}
		if (roleService.add(role) <=0) {
			ret.put("type", "error");
			ret.put("msg", "角色添加失败，清联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "角色添加成功！");
		return ret;
	}
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Role role){
		Map<String, String> ret = new HashMap<String, String>();
		if (role == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的角色信息！");
			return ret;
		}
		if (StringUtils.isEmpty(role.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写角色名称！");
			return ret;
		}
		if (roleService.edit(role) <=0) {
			ret.put("type", "error");
			ret.put("msg", "角色修改失败，清联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "角色修改成功！");
		return ret;
	}
	/**
	 * 删除角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(Long id){
		Map<String, String> ret = new HashMap<String, String>();
		if (id == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择要删除的角色信息！");
			return ret;
		}
		try {
			if (roleService.delete(id) <=0) {
				ret.put("type", "error");
				ret.put("msg", "角色删除失败，清联系管理员！");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该角色下存在权限或者用户信息，不允许删除！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "角色删除成功！");
		return ret;
	}
	/**
	 * 获取所有的菜单信息
	 * @return
	 */
	@RequestMapping(value="/get_all_menu",method=RequestMethod.POST)
	@ResponseBody
	public List<Menu> getAllMenu() {
		//设置页面显示查询信息数量
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 99);
		return menuService.findList(queryMap);
	}
	/**
	 * 添加权限信息
	 * @return
	 */
	@RequestMapping(value="/add_authority",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addAuthority(
			@RequestParam(name="ids",required=true) String ids,
			@RequestParam(name="roleId",required=true) Long roleId
			){
		Map<String, String> ret = new HashMap<String, String>();
		if (StringUtils.isEmpty(ids)) {
			ret.put("type", "error");
			ret.put("msg", "请选择要添加的权限！");
			return ret;
		}
		if (StringUtils.isEmpty(roleId)) {
			ret.put("type", "error");
			ret.put("msg", "请选择相应的角色！");
			return ret;
		}
		//将权限之间用，隔开
		String[] idArr = ids.split(",");
		//判断是否选择权限
		if (idArr.length>0) {
			//表示已经选择了权限，将权限删除，防止与后面添加的重复
			authorityService.deleteByRoleId(roleId);
		}
		//遍历，添加已经选择的权限
		for (String id:idArr) {
			Authority authority = new Authority();
			authority.setMenuId(Long.valueOf(id));
			authority.setRoleId(roleId);
			authorityService.add(authority);
		}
		ret.put("type", "success");
		ret.put("msg", "权限编辑成功！");
		return ret;
	}
	/**
	 * 获取某个角色的所有权限的方法
	 */
	@RequestMapping(value="/get_role_authority",method=RequestMethod.POST)
	@ResponseBody
	public List<Authority> getRoleAuthority(
			@RequestParam(name="roleId",required=true) Long roleId
			//@RequestParam(name="roleId",required=true) Long roleId
			){
		return authorityService.findListByRoleId(roleId);
	}
}
