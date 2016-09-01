package com.taotao.item.listener;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.taotao.item.pojo.Iterm;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItermService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemListenerFreeMarker implements MessageListener{
	@Value("${ITEM_PATH}")
	private String ITEM_PATH;
	@Autowired
	private ItermService itermService;
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	@Override
	public void onMessage(Message message) {
		try {
			//当前线程等待保证数据库业务提交后再执行
			Thread.sleep(100);
			
			TextMessage textMessage = (TextMessage) message;
			//如果接收到了消息就更新缓存内容
			if(textMessage != null && !"".equals(textMessage)) {
				String sid = textMessage.getText();
				Long id = Long.parseLong(sid);
				//根据id查询商品信息
				TbItem tbItem = itermService.findTBItemById(id);
				TbItemDesc itemDesc = itermService.findTBItemDescById(id);
				Iterm item = new  Iterm(tbItem);
				//将商品详情页面静态化
				//1.从spring容器中获的FreeMarkerConfig对象
				//2.从FreeMarkerConfig对象中获得configuration对象
				//3.使用configuration获得Template对象
				//4.创建数据集
				//5.创建输出文件的Writer对象
				//6.调用模板对象的process方法,生成文件
				//7.关闭流
				Configuration configuration = freeMarkerConfig.getConfiguration();
				Template template = configuration.getTemplate("item.ftl");
				//通过调用itemService接口获得数据集
				Map model = new HashMap<>();
				model.put("item", item);
				model.put("itemDesc", itemDesc);
				//页面静态化
				Writer writer = new FileWriter(ITEM_PATH+id+".html");
				template.process(model, writer);
				writer.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
