package com.bbu.cl.controller.home;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RespectBinding;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bbu.cl.entity.Account;
import com.bbu.cl.service.AccountService;
import com.bbu.cl.service.RoomTypeService;

/**
 * 前台主页控制器
 * @author 疯人愿
 *
 */
@Controller
@RequestMapping(value="/home")
public class HomeController {
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private AccountService accountService;
	/**
	 * 酒店管理前台首页
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model,
			@RequestParam(name="name",required=false)String name
			) {
	Map<String, Object> queryMap = new HashMap<String, Object>();
	queryMap.put("name", name);
	queryMap.put("offset", 0);
	queryMap.put("pageSize", 99);
	model.addObject("roomTypeList", roomTypeService.findList(queryMap));
	model.setViewName("/home/index/index");
	return model;
	}
	/**
	 * 登录页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
	model.setViewName("/home/index/login");
	return model;
	}
	/**
	 * 注册页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public ModelAndView register(ModelAndView model) {
	model.setViewName("/home/index/register");
	return model;
	}
	/**
	 * 登录信息提交
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> loginAct(Account account,String cpacha,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if (account == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的用户信息！");
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
		if (StringUtils.isEmpty(cpacha)) {
			ret.put("type", "error");
			ret.put("msg", "验证码不能为空！"); 
			return ret;
		}
		Object attribute = request.getSession().getAttribute("loginCpacha");
		if (attribute == null) {
			ret.put("type", "error");
			ret.put("msg", "验证码过期，请刷新重试！");
			return ret;
		}
		if (!cpacha.equalsIgnoreCase(attribute.toString())) {
			ret.put("type", "error");
			ret.put("msg", "验证码错误！");
			return ret;
		}
		Account findByUsername = accountService.findByUsername(account.getUsername());
		if (StringUtils.isEmpty(findByUsername)) {
		//if (findByUsername == null) {
			ret.put("type", "error");
			ret.put("msg", "用户名不存在！");
			return ret;
		}
		if (!account.getPassword().equals(findByUsername.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "密码不正确！");
			return ret;
		}
		if (findByUsername.getStatus() == -1) {
			ret.put("type", "error");
			ret.put("msg", "该用户存在不良记录，已被禁用，请联系管理员！");
			return ret;
		}
		request.getSession().setAttribute("account", findByUsername);
		request.getSession().setAttribute("loginCpacha", null);
		ret.put("type", "success");
		ret.put("msg", "登录成功！");
		return ret;
	}
	/**
	 * 注册信息提交
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> registerAct(Account account){
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
		if (isExist(account.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "该用户名已经存在！");
			return ret;
		}
		if (accountService.add(account) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "注册失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "注册成功！");
		return ret;
	}
	/**
	 * 用户注销登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest request,HttpServletResponse response){
		request.getSession().setAttribute("account", null);
		return "redirect:login";
	}
	//判断用户名是否存在
	private boolean isExist(String username){
		Account findByUsername = accountService.findByUsername(username);
		if(findByUsername == null)return false;
		return true;
	}
}
