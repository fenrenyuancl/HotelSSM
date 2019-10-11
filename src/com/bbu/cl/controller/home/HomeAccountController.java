package com.bbu.cl.controller.home;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bbu.cl.entity.Account;
import com.bbu.cl.entity.BookOrder;
import com.bbu.cl.entity.RoomType;
import com.bbu.cl.service.AccountService;
import com.bbu.cl.service.BookOrderService;
import com.bbu.cl.service.RoomTypeService;

/**
 * 前台用户主页控制器
 * @author 疯人愿
 *
 */
@Controller
@RequestMapping(value="/home/account")
public class HomeAccountController {
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BookOrderService bookOrderService;
	/**
	 * 前台用户中心首页
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model,HttpServletRequest request) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		Account account = (Account) request.getSession().getAttribute("account");
		queryMap.put("accountId", account.getId());
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 999);
		model.addObject("bookOrderList", bookOrderService.findList(queryMap));
		model.addObject("roomTypeList", roomTypeService.findAll());
		model.setViewName("/home/account/index");
		return model;
	}
	/**
	 * 预定房间页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/book_order",method=RequestMethod.GET)
	public ModelAndView bookOrder(ModelAndView model,Long roomTypeId) {
		model.addObject("roomType",roomTypeService.find(roomTypeId));
		model.setViewName("/home/account/book_order");
		return model;
	}
	/**
	 * 预定房间信息提交
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/book_order",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> bookOrderAct(BookOrder bookOrder,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if (bookOrder == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的预定订单信息！");
			return ret;
		}
		Account account = (Account) request.getSession().getAttribute("account");
		if (StringUtils.isEmpty(account)) {
			ret.put("type", "error");
			ret.put("msg", "客户不能为空！");
			return ret;
		}
		bookOrder.setAccountId(account.getId());
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
		bookOrder.setStatus(0);//设置房间状态为预定中
		if (bookOrderService.add(bookOrder) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "预定失败，请联系管理员！");
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
		ret.put("msg", "预定成功！");
		return ret;
	}

	/**
	 * 修改个人信息提交
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/update_info",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateInfoAct(Account account,HttpServletRequest request){
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
		if (StringUtils.isEmpty(account.getIdCard())) {
			ret.put("type", "error");
			ret.put("msg", "身份证号不能为空！");
			return ret;
		}
		/*if (StringUtils.isEmpty(account.getPhoneNum())) {
			ret.put("type", "error");
			ret.put("msg", "手机号不能为空！");
			return ret;
		}*/
		Account loginedAccount = (Account) request.getSession().getAttribute("account");
		if (isExist(account.getUsername(),loginedAccount.getId())) {
			ret.put("type", "error");
			ret.put("msg", "该用户名已经存在！");
			return ret;
		}
		loginedAccount.setUsername(account.getUsername());
		loginedAccount.setName(account.getName());
		loginedAccount.setIdCard(account.getIdCard());
		loginedAccount.setPhoneNum(account.getPhoneNum());
		loginedAccount.setAddress(account.getAddress());
		if (accountService.edit(loginedAccount) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "修改失败，请联系管理员！");
			return ret;
		}
		//更新session中的account
		request.getSession().setAttribute("account", loginedAccount);
		ret.put("type", "success");
		ret.put("msg", "修改成功！");
		return ret;
	}
	/**
	 * 修改密码
	 * @param oldpassword
	 * @param newpassword
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update_pwd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updatePwdAct(String oldpassword,String newpassword,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if (oldpassword == null) {
			ret.put("type", "error");
			ret.put("msg", "原密码不能为空！");
			return ret;
		}
		if (newpassword == null) {
			ret.put("type", "error");
			ret.put("msg", "新密码不能为空！");
			return ret;
		}
		Account account = (Account) request.getSession().getAttribute("account");
		if (!oldpassword.equals(account.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "原密码不正确！");
			return ret;
		}
		account.setPassword(newpassword);
	
		if (accountService.edit(account) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "修改失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "修改成功！");
		return ret;
	}
	//判断用户名是否存在
	private boolean isExist(String username,Long id){
		Account account = accountService.findByUsername(username);
		if(account == null)return false;
		if(account.getId().longValue() == id)return false;
		return true;
	}
}
