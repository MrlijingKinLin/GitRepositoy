package com.taotao.item.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.taotao.common.utils.JsonUtils;
import com.taotao.item.pojo.Iterm;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.redis.JedisClient;
import com.taotao.service.ItermService;

public class ItemListener implements MessageListener {
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	@Value("${ITEM_INFO_EXPIRE}")
	private Integer ITEM_INFO_EXPIRE;
	@Autowired
	JedisClient jedisClient;
	@Autowired
	private ItermService itermService;
	@Override
	public void onMessage(Message message) {
		try {
			//当前线程等待保证数据库业务提交后再执行
			Thread.sleep(100);
			// 添加商品更新redis缓存
			TextMessage textMessage = (TextMessage) message;
			//如果接收到了消息就更新缓存内容
			if(textMessage != null && !"".equals(textMessage)) {
				String sid = textMessage.getText();
				Long id = Long.parseLong(sid);
				//根据id查询商品信息
				TbItem tbItem = itermService.findTBItemById(id);
				TbItemDesc itemDesc = itermService.findTBItemDescById(id);
				Iterm iterm = new  Iterm(tbItem);
				//将商品信息放入缓存中需要设置缓存时间
				jedisClient.set(ITEM_INFO+":"+id+":base", JsonUtils.objectToJson(iterm));
				jedisClient.set(ITEM_INFO+":"+id+":desc", JsonUtils.objectToJson(itemDesc));
				jedisClient.expire(ITEM_INFO,ITEM_INFO_EXPIRE);
				jedisClient.expire(ITEM_INFO,ITEM_INFO_EXPIRE);
				System.out.println("通过消息队列将商品详情信息添加到了缓存");
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
