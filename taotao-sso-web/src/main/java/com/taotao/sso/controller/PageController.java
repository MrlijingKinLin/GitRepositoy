package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.CookieUtils;
	
@Controller
public class PageController {
	@Value("${USER_TOKEN_KEY}")
	private String USER_TOKEN_KEY;
	
	@RequestMapping("/user/showLogin")
	public String loginUI(String redirectUrl,Model model) {
			model.addAttribute("redirect", redirectUrl);
			return "login";
	}
	@RequestMapping("/user/showRegister")
	public String registerUI() {
		return "register";
	}
	
	@RequestMapping("/user/logout.html")
	public String deleteUserCookie(HttpServletRequest request, HttpServletResponse response){
		CookieUtils.deleteCookie(request, response, USER_TOKEN_KEY);
		return "login";
	}
}
