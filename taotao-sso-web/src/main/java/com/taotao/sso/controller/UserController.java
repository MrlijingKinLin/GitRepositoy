package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.redis.JedisClient;
import com.taotao.sso.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JedisClient jedisClient;
	@Value("${TT_USER_TOKEN}")
	private String TT_USER_TOKEN;
	@Value("${TOKEN_EXPIRE}")
	private Integer TOKEN_EXPIRE;
	@Value("${USER_TOKEN_KEY}")
	private String USER_TOKEN_KEY;

	/**
	 * 校验数据
	 * 
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping("/user/check/{param}/{type}")
	public TaotaoResult UserCheck(@PathVariable String param, @PathVariable Integer type) {
		TaotaoResult result = userService.UserCheck(param, type);
		return result;
	}

	/**
	 * 用户注册
	 * 
	 * @param tbUser
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/register")
	public TaotaoResult UserRegister(TbUser tbUser) {
		TaotaoResult result = userService.UserRegister(tbUser);
		return result;
	}
	/**用户登录
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/login")
	public TaotaoResult UserLogin(String username,String password, HttpServletRequest request, HttpServletResponse response){
		TaotaoResult result = userService.userLogin(username,password);
		if (result.getStatus() == 200) {
			// 把用户信息放到cookie中
			String token = (String) result.getData();
			// 将token信息放入cookie中
			CookieUtils.setCookie(request, response, USER_TOKEN_KEY, token);
		}
		return result;
	}
	
	/**通过token查询用户信息
	 * @param token
	 * @return如何解决json跨域问题
	 */
	@RequestMapping(value="/user/token/{token}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String UserToken(@PathVariable String token,String callback) {
		TaotaoResult result = userService.searchUserByToken(token);
		//是否为jsonp请求
		if(StringUtils.isNotBlank(callback)){
			String strResult = callback+"("+JsonUtils.objectToJson(result)+")";
			return strResult;
		}
		return JsonUtils.objectToJson(result);
	}
	
	
	/*@RequestMapping("/user/token/{token}") 
	public Object Usertoken(@PathVariable String token,String callback){
		TaotaoResult result = userService.searchUserByToken(token);
		if(StringUtils.isNotBlank(callback)){
			//支持jsonp
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			
			mappingJacksonValue.setJsonpFunction(callback);
			return  mappingJacksonValue;
		}
		return result;
	}*/
	
	
	
}
