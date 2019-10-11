package com.bbu.cl.controller.admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.bbu.cl.entity.admin.User;
import com.bbu.cl.service.admin.AuthorityService;
import com.bbu.cl.service.admin.LogService;
import com.bbu.cl.service.admin.MenuService;
import com.bbu.cl.service.admin.RoleService;
import com.bbu.cl.service.admin.UserService;
import com.bbu.cl.utils.CpachaUtil;
import com.bbu.cl.utils.MenuUtil;

/**
 * 系统操作类控制器
 * @author 疯人愿
 *
 */
@Controller
@RequestMapping("/system")
public class SystemController {
	//引入用户Service
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private LogService logService;
	
	//系统登录后的系统主页面
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView model,HttpServletRequest request){
		List<Menu> userMenus = (List<Menu>)request.getSession().getAttribute("userMenus");
		model.addObject("topMenuList", MenuUtil.getAllTopMenu(userMenus));
		model.addObject("secondMenuList", MenuUtil.getAllSecondMenu(userMenus));
		model.setViewName("system/index");
		return model;//WEB-INF/views/+system/index+.jsp = WEB-INF/views/system/index.jsp
	}
	//系统登录后的首页欢迎页面
	@RequestMapping(value="/welcome",method=RequestMethod.GET)
	public ModelAndView welcome(ModelAndView model) {
		model.setViewName("system/welcome");
		return model;
	}
	//进入登录页面
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		model.setViewName("system/login");
		return model;
	}
	//登录
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> loginAct(User user,String cpacha,HttpServletRequest request) {
		Map<String,String> ret = new HashMap<String,String>();
		if (user == null) {
			ret.put("type","error");
			ret.put("msg","请填写用户信息!");
			return ret;
		}
		if (StringUtils.isEmpty(cpacha)) {
			ret.put("type","error");
			ret.put("msg","请填写验证码!");
			return ret;
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type","error");
			ret.put("msg","请填写用户名!");
			return ret;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type","error");
			ret.put("msg","请填写密码!");
			return ret;
		}
		Object loginCpacha = request.getSession().getAttribute("loginCpacha");
		if (loginCpacha == null) {
			ret.put("type","error");
			ret.put("msg","长时间未操作，会话超时，请刷新后重试!");
			return ret;
		}
		if (!cpacha.toUpperCase().equals(loginCpacha.toString().toUpperCase())) {
			ret.put("type","error");
			ret.put("msg","验证码错误");
			//logService.add("用户名为"+user.getUsername()+"的用户在输入时验证码错误！");
			return ret;
		}
		User findByUsername = userService.findByUsername(user.getUsername());
		//判断用户名是否存在
		if (findByUsername == null) {
			ret.put("type","error");
			ret.put("msg","该用户不名存在！");
			return ret;
		}
		//如果用户名存在则判断密码是否正确
		if (!user.getPassword().equals(findByUsername.getPassword())) {
			ret.put("type","error");
			ret.put("msg","密码错误！");
			logService.add("用户名为"+user.getUsername()+"的用户在输入时密码错误！");
			return ret;
		}
		//说明用户名密码和验证码都正确
		//此时需要查询用户的角色的权限
		Role role = roleService.find(findByUsername.getRoleId());
		//根据权限获取角色列表
		List<Authority> authorityList = authorityService.findListByRoleId(role.getId());
		String menuIds = "";
		for(Authority authority:authorityList){
			menuIds += authority.getMenuId() + ",";
		}
		if(!StringUtils.isEmpty(menuIds)){
			menuIds = menuIds.substring(0,menuIds.length()-1);
		}
		List<Menu> userMenus = menuService.findListByIds(menuIds);
		//把角色信息、菜单信息放到session中
		request.getSession().setAttribute("admin", findByUsername);
		request.getSession().setAttribute("role", role);
		request.getSession().setAttribute("userMenus", userMenus);
		
		ret.put("type","success");
		ret.put("msg","登录成功!");
		logService.add("欢迎"+user.getUsername()+"的用户登录成功！");
		return ret;
	}
	/**
	 * 获取验证码
	 * 该系统所有验证码均采用此方法
	 * @param vcodeLen 		验证码个数长度
	 * @param width			验证码图片宽度
	 * @param height		验证码图片高度
	 * @param cpachaType	验证码的类型，传入字符串
	 * @param request		
	 * @param response
	 */
	@RequestMapping(value="/get_cpacha",method=RequestMethod.GET)
	public void generateCpacha(
			@RequestParam(name="vl",required = false,defaultValue="4") Integer vcodeLen,
			@RequestParam(name="w",required = false,defaultValue="100") Integer width,
			@RequestParam(name="h",required = false,defaultValue="30") Integer height,
			@RequestParam(name="type",required = true,defaultValue="30") String cpachaType,
			HttpServletRequest request,
			HttpServletResponse response) {
		CpachaUtil cpachaUtil = new CpachaUtil(vcodeLen, width, height);
		//生成一个验证码
		String generatorVCode = cpachaUtil.generatorVCode();
		//获取session，将验证码信息传入session
		request.getSession().setAttribute(cpachaType, generatorVCode);
		//生成一个验证码图片 true表示画干扰线
		BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			//生成的图片以流的形式写到response的输出流中
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//系统登录后的首页欢迎页面
	@RequestMapping(value="/edit_password",method=RequestMethod.GET)
	public ModelAndView editPassword(ModelAndView model) {
		model.setViewName("system/edit_password");
		return model;
	}
	/**
	 * 修改密码方法
	 * @param newpassword
	 * @param oldpassword
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edit_password",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editPasswordAct(String newpassword,String oldpassword,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmpty(newpassword)){
			ret.put("type", "error");
			ret.put("msg", "请填写新密码！");
			return ret;
		}
		User user = (User)request.getSession().getAttribute("admin");
		if(!user.getPassword().equals(oldpassword)){
			ret.put("type", "error");
			ret.put("msg", "原密码错误！");
			return ret;
		}
		user.setPassword(newpassword);
		if(userService.editPassword(user) <= 0){
			ret.put("type", "error");
			ret.put("msg", "密码修改失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "密码修改成功！");
		logService.add("用户名为{"+user.getUsername()+"}，的用户成功修改密码!");
		return ret;
	} 
	
	
	
	/**
	 * 退出功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("admin", null);
		session.setAttribute("role", null);
		request.getSession().setAttribute("userMenus", null);
		return "redirect:login";
	}
}
