package com.bbu.cl.interceptor.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bbu.cl.entity.admin.Menu;
import com.bbu.cl.utils.MenuUtil;

import net.sf.json.JSONObject;
/**
 * 后台登陆拦截器
 * @author 疯人愿
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean preHandle(	HttpServletRequest request,
								HttpServletResponse response,
								Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI();
		//获取登录后的用户，将用户信息传入admin全局变量中
		Object admin = request.getSession().getAttribute("admin");
		//判断用户是否登录
		if (admin == null) {
			//用户未登录,转发到登录界面
			System.out.println(requestURI+"进入拦截器");
			//判断是否为ajax请求（如果是ajax请求响应头会有x-requested-with ）
			String header = request.getHeader("X-Requested-With");
			if ("XMLHttpRequest".equals(header)) {
				//ajax请求
				Map<String, String> ret = new HashMap<String , String>();
				ret.put("type", "error");
				ret.put("msg", "会话超时，登录状态已失效，请重新登录！");
				response.getWriter().write(JSONObject.fromObject(ret).toString());
				return false;
			};
			//表示是普通链接，直接重定向到登录界面	
			response.sendRedirect(request.getServletContext().getContextPath()+"/system/login");
			return false;
		}
		//获取菜单id
		String mid = request.getParameter("_mid");
		if (!StringUtils.isEmpty(mid)) {
			List<Menu> allThirdMenu = MenuUtil.getAllThirdMenu((List<Menu>)request.getSession().getAttribute("userMenus"), Long.valueOf(mid));
			request.setAttribute("thirdMenuList", allThirdMenu);
		}
		return true;
	}
}
