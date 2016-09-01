package com.taotao.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.sso.service.UserService;

import freemarker.template.utility.StringUtil;

public class OrderLoginInterceptor implements HandlerInterceptor {
	@Value("${TT_USER_TOKEN}")
	private String TT_USER_TOKEN;
	@Value("${SSO_URL}")
	private String SSO_URL;
	@Value("${ORDER_SUBMITURL}")
	private String ORDER_SUBMITURL;
	@Autowired
	private UserService userService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 在handler执行之前 判断用户是否登录
		String token = CookieUtils.getCookieValue(request, TT_USER_TOKEN);
		if(StringUtils.isBlank(token)){
			//返回用户登录页面单点服务系统的单点登录,登录成功后需要跳回订单支付页面
			String url = request.getRequestURI().toString();
			response.sendRedirect(SSO_URL+"/user/login.html?redirectUrl="+url);
			return false;
		}
		TaotaoResult result = userService.searchUserByToken(token);
		if(result.getStatus()==400){
			//返回用户登录页面单点服务系统的单点登录,登录成功后需要跳回订单支付页面
			String url = request.getRequestURI().toString();
			response.sendRedirect(SSO_URL+"/user/login.html?redirectUrl="+url);
			return false;
		}
		//如果用户已经登录放行,把当前用户信息存入request域中
		request.setAttribute("user", result.getData());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {
		// 在handler执行之后 modelandView视图返回前

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 在handler执行之前 modelandView视图返回后 只能做异常处理和日志记录等

	}

}
