package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.dao.TbUserMapper;
import com.taotao.jedis.JedisClient;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Value("${TT_USER_TOKEN}")
	private String TT_USER_TOKEN;
	@Value("${TOKEN_EXPIRE}")
	private Integer TOKEN_EXPIRE;
	@Autowired
	JedisClient jedisClient;
	@Autowired
	private TbUserMapper tbUserMapper;

	@Override
	public TaotaoResult UserCheck(String param, int type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 可选参数1、2、3分别代表username、phone、email
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		} else if (type == 3) {
			criteria.andEmailEqualTo(param);
		} else {
			return TaotaoResult.build(400, "非法的参数");
		}
		List<TbUser> list = tbUserMapper.selectByExample(example);
		// 如果查询到的结果是null后者大小是0 可用
		if (list.size() == 0 || list == null) {
			return TaotaoResult.ok(true);
		} else {
			return TaotaoResult.ok(false);
		}
	}

	@Override
	public TaotaoResult UserRegister(TbUser tbUser) {
		// 用户注册需要校验用户信息
		// 如果用户名为空
		if (StringUtils.isBlank(tbUser.getPhone())) {
			return TaotaoResult.build(400, "用户名电话不能为空");
		}
		if (StringUtils.isBlank(tbUser.getUsername())) {
			return TaotaoResult.build(400, "用户名不能为空");
		}

		// 1.如果用户名存在

		if (!(boolean) UserCheck(tbUser.getUsername(), 1).getData()) {

			return TaotaoResult.build(400, "注册失败用户名已被使用");
		}

		// 2.如果用户电话存在

		if (!(boolean) UserCheck(tbUser.getPhone(), 2).getData())

			return TaotaoResult.build(400, "注册失败,用户手机号已存在");

		// 3.如果用户邮箱存在

		// 校验email是否可用
		if (StringUtils.isNotBlank(tbUser.getEmail())) {

			if (!(boolean) UserCheck(tbUser.getEmail(), 3).getData()) {
				return TaotaoResult.build(400, "此邮件地址已经被使用");
			}
		}
		// 4.如果用户密码为空
		if (tbUser.getPassword() == null || "".equals(tbUser.getPassword())) {
			return TaotaoResult.build(400, "注册失败,用户密码不能 为空", null);
		}

		// 5.如果通过校验补全用户信息,添加到数据库并返回数据
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());
		// 用户密码加密使用spring自带的密码加密通用版化
		String password = tbUser.getPassword();
		String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
		tbUser.setPassword(md5Password);
		tbUserMapper.insert(tbUser);
		return TaotaoResult.ok();
	}

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public TaotaoResult userLogin(String username, String password) {
		// 通过用户名查询用户信息
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		//这里要用||我判断错了
		if (list == null || list.size() == 0) {
			return TaotaoResult.build(400, "用户名不存在");
		}
		TbUser tbUser = list.get(0);
		String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
		String password1 = tbUser.getPassword();
		if (!password1.equals(md5Password)) {
			return TaotaoResult.build(400, "用户密码错误");
		}
		//登录成功后生成token
		String token = UUID.randomUUID().toString();
		tbUser.setPassword(null);
		jedisClient.set(TT_USER_TOKEN+":"+token, JsonUtils.objectToJson(tbUser));
		jedisClient.expire(TT_USER_TOKEN,TOKEN_EXPIRE);
		//返回taotaoresult包装token
		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult searchUserByToken(String token) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(token)) {
			return TaotaoResult.build(400, "当前登录用户已失效请重新登录", null);
		}
		String string = jedisClient.get(TT_USER_TOKEN + ":" + token);
		if (StringUtils.isBlank(string)) {
			return TaotaoResult.build(400, "当前登录用户已失效请重新登录", null);
		}
		TbUser tbUser = JsonUtils.jsonToPojo(string, TbUser.class);
		tbUser.setPassword(null);
		return TaotaoResult.ok(tbUser);
	}

	@Override
	public TaotaoResult userLogOut(String token) {
		// 从redis中移除
		jedisClient.del(TT_USER_TOKEN + ":" + token);
		return TaotaoResult.ok();
	}

}
