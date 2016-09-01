package com.taotao.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.search.service.SearchItemService;

public class ActiveMQListener implements MessageListener {
	/**
	 * 接收id信息通过id查询数据库进行索引更新 生产者由manager服务层提供
	 */
	@Autowired
	SearchItemService searchItemService;

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			
			Thread.sleep(200);
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
			
					String text = textMessage.getText();// 获得消息队列中的内容
					Long id = Long.parseLong(text);// 强转Long 根据id查询数据库更新索引信息

					TaotaoResult result = searchItemService.addItemById(id);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
