package com.taotao.sso.service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbUser;


public interface UserService {
	/**
	 * 用户注册校验
	 * @param param 参数
	 * @param type 类型可选参数1、2、3分别代表username、phone、email
	 * @return
	 */
	public TaotaoResult UserCheck(String param,int type);
	
	
	/**用户注册
	 * @param tbUser   快捷键 alt+shift+j
	 * @return
	 */
	public TaotaoResult UserRegister(TbUser tbUser);
	
	/**用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public TaotaoResult userLogin(String username,String password);
	/**通过token查询用户信息
	 * @param token
	 * @return
	 */
	public TaotaoResult searchUserByToken(String token);
	/**用户退出登录
	 * @return
	 */
	public TaotaoResult userLogOut(String token);
	
}
