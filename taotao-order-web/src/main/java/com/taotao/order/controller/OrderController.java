package com.taotao.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
/**
 * 添加订单的controller
 * @author Mrlijing
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.order.pojo.Iterm;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Value("${TBITEM_COOCKIE}")
	private String TBITEM_COOCKIE;
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request,HttpServletResponse response){
		
			//取用户id
			//从cookie中取token，然后根据token查询用户信息。需要调用sso系统的服务。
			//根据用户id查询收货地址列表
			//从cookie中取商品列表
			List<Iterm> items = new ArrayList<>();
			List<TbItem> cartList = getCartList(request);
			for (TbItem tbItem : cartList) {
				Iterm iterm = new Iterm();
				items.add(iterm);
			}
			//传递给页面
			request.setAttribute("cartList", items);
			//返回逻辑视图
			return "order-cart";
		
	}
	//返回购物车信息，在拦截器中设置request域用户信息
	
	
	
	private  List<TbItem> getCartList(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, TBITEM_COOCKIE, true);
		// 如果cookie中没有返回空的列表
		if (StringUtils.isBlank(cookieValue)) {
			return new ArrayList<>();
		}
		List<TbItem> list = JsonUtils.jsonToList(cookieValue, TbItem.class);
		return list;
	}
}
