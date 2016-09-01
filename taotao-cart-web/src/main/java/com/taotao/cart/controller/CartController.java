package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.cart.pojo.Iterm;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItermService;

/**
 * 购物车controller
 * 
 * @author Mrlijing
 *
 */
@Controller
public class CartController {
	@Value("${TBITEM_COOCKIE}")
	private String TBITEM_COOCKIE;
	@Value("${COOKIE_MAXAGE}")
	private Integer COOKIE_MAXAGE;
	@Autowired
	private ItermService itermService;

	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		// 先从cookie中获取商品列表,如果没有设置数量为num 如果有数量相加
		List<TbItem> tbItems = getCartList(request);
		// 遍历商品列表
		boolean hasItem = false;
		for (TbItem tbItem : tbItems) {
			// 如果有数量相加
			// 对象比较的是地址，应该通过值来比较
			if (tbItem.getId() == itemId.longValue()) {
				tbItem.setNum(tbItem.getNum() + num);
				hasItem = true;
				break;
			}
		}
		// 如果没有将商品添加到cookie中
		if (!hasItem) {
			// 为了信息安全这里选择编码
			TbItem tbItem = itermService.findTBItemById(itemId);
			tbItem.setNum(num);
			// 将商品信息添加到购物车列表
			tbItems.add(tbItem);
			CookieUtils.setCookie(request, response, TBITEM_COOCKIE, JsonUtils.objectToJson(tbItems), COOKIE_MAXAGE,
					true);
		}
		return "cartSuccess";
	}

	@RequestMapping("/cart/cart")
	public String showCart(HttpServletRequest request) {
		// 从cookie中获取所有的商品列表并展示到页面
		List<TbItem> tbItems = getCartList(request);
		List<Iterm> iterms = new ArrayList<>();
		for (TbItem tbItem : tbItems) {
			Iterm iterm = new Iterm(tbItem);
			iterms.add(iterm);
		}
		request.setAttribute("cartList", iterms);
		return "cart";
	}

	/**
	 * 更新商品信息
	 * 
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateCart(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		List<TbItem> tbItems = getCartList(request);
		// 如果有数量加上num
		boolean hasItem = false;
		for (TbItem tbItem : tbItems) {
			if (tbItem.getId() == itemId.longValue()) {
				tbItem.setNum(tbItem.getNum() + num);
				hasItem = true;
				break;
			}
		}
		CookieUtils.setCookie(request, response, TBITEM_COOCKIE, JsonUtils.objectToJson(tbItems), COOKIE_MAXAGE, true);
		// 页面需要返回data
		return TaotaoResult.ok();
	}

	// 删除商品需要重定向 到购物车
	@RequestMapping("/cart/delete/{itemId}")
	public String deleleCart(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		List<TbItem> tbItems = getCartList(request);
		for (TbItem tbItem : tbItems) {
			if (tbItem.getId() == itemId.longValue()) {
				tbItems.remove(tbItem);
			}
		}

		CookieUtils.setCookie(request, response, TBITEM_COOCKIE, JsonUtils.objectToJson(tbItems), COOKIE_MAXAGE, true);
		return "redirect:/cart/cart.html";
	}

	private List<TbItem> getCartList(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, TBITEM_COOCKIE, true);
		// 如果cookie中没有返回空的列表
		if (StringUtils.isBlank(cookieValue)) {
			return new ArrayList<>();
		}
		List<TbItem> list = JsonUtils.jsonToList(cookieValue, TbItem.class);
		return list;
	}
}
