package com.taotao.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

public class GlobalExceptionResolver implements HandlerExceptionResolver{
	/**
	 * 全局异常处理
	 */
	public static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception e) {
		//异常处理
		//写日志
		LOGGER.debug("debug模式已开启");
		LOGGER.error("系统发生异常",e);
		
		//发邮件
		
		//发短信
		
		//转到全局异常错误处理页面
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("messager", "系统发生异常,请稍后再试");
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}
	
}
