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

import com.bbu.cl.entity.Account;
import com.bbu.cl.page.admin.Page;
import com.bbu.cl.service.AccountService;
/**
 * 客户管理后台控制器
 * @author 疯人愿
 *
 */
@Controller
@RequestMapping("/admin/account")
public class AccountController {
	// 引入Service
	@Autowired
	private AccountService accountService;
	/**
	 * 获取客户页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	private ModelAndView List(ModelAndView model) {
		model.setViewName("/account/list");
		return model;
	}

	/**
	 * 获取客户列表
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
			@RequestParam(name = "username", required = false, defaultValue = "") String username,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "idCard", required = false) String idCard,
			@RequestParam(name = "phoneNum", required = false) String phoneNum,
			@RequestParam(name = "status", required = false) Integer status) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		queryMap.put("username", username);
		queryMap.put("name", name);
		queryMap.put("idCard", idCard);
		queryMap.put("phoneNum", phoneNum);
		queryMap.put("status", status);
		List<Account> findList = accountService.findList(queryMap);
		ret.put("rows", findList);
		ret.put("total", accountService.getTotal(queryMap));
		return ret;
	}

	/**
	 * 添加客户
	 * 
	 * @param roomType
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Account account) {
		Map<String, String> ret = new HashMap<String, String>();
		if (account == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的客户信息！");
			return ret;
		}
		if (StringUtils.isEmpty(account.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "用户名不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(account.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "密码不能为空！");
			return ret;
		}
		/*if (StringUtils.isEmpty(account.getIdCard())) {
			ret.put("type", "error");
			ret.put("msg", "身份证号不能为空！");
			return ret;
		}*/
		if (StringUtils.isEmpty(account.getPhoneNum())) {
			ret.put("type", "error");
			ret.put("msg", "手机号不能为空！");
			return ret;
		}
		if (isExist(account.getUsername(), 0l)) {
			ret.put("type", "error");
			ret.put("msg", "该用户名已经存在！");
			return ret;
		}
		if (accountService.add(account) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功！");
		return ret;
	}

	/**
	 * 修改客户
	 * 
	 * @param roomType
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Account account) {
		Map<String, String> ret = new HashMap<String, String>();
		if (account == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的客户信息！");
			return ret;
		}
		if (StringUtils.isEmpty(account.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "用户名不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(account.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "密码不能为空！");
			return ret;
		}
	/*	if (StringUtils.isEmpty(account.getIdCard())) {
			ret.put("type", "error");
			ret.put("msg", "身份证号不能为空！");
			return ret;
		}*/
		if (StringUtils.isEmpty(account.getPhoneNum())) {
			ret.put("type", "error");
			ret.put("msg", "手机号不能为空！");
			return ret;
		}
		if (isExist(account.getUsername(), account.getId())) {
			ret.put("type", "error");
			ret.put("msg", "该用户名已经存在！");
			return ret;
		}
		if (accountService.edit(account) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "修改失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "修改成功！");
		return ret;
	}
	/**
	 * 客户删除
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
			ret.put("msg", "请选择要删除的客户信息！");
			return ret;
		}
		try {
			if (accountService.delete(id) <= 0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员！");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该客户下存在订单信息，请先删除该客户下的所有订单信息！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功！");
		return ret;
	}
	//判断用户名是否存在
	private boolean isExist(String username,Long id){
		Account findByUsername = accountService.findByUsername(username);
		if(findByUsername == null)return false;
		if(findByUsername.getId().longValue() == id.longValue())return false;
		return true;
	}
}
