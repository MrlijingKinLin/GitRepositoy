package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIPageResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.dao.TbItemDescMapper;
import com.taotao.dao.TbItemMapper;
import com.taotao.dao.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItermService;

@Service
public class ItermServiceImpl implements ItermService {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	/**
	 * 通过id查找商品
	 */
	@Override
	public TbItem findTBItemById(Long id) {
		// TODO Auto-generated method stub
		TbItem item = tbItemMapper.selectByPrimaryKey(id);
		return item;
	}

	/**
	 * 查询所有商品
	 */
	@Override
	public List<TbItem> findTbItemByExample() {
		// TODO Auto-generated method stub
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		return list;
	}

	/**
	 * 分页查询
	 */
	@Override
	public EasyUIPageResult getEasyUIResult(int page, int rows) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		// 要被分页的数据
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		PageInfo<TbItem> info = new PageInfo<>(list);
		// 获取分页信息
		EasyUIPageResult result = new EasyUIPageResult();
		// 设置分页
		result.setTotal((int) info.getTotal());
		result.setRows(list);
		return result;
	}

	/**
	 * 商品描述信息查询
	 */
	@Override
	public TbItemDesc findTBItemDescById(Long id) {
		// TODO Auto-generated method stub
		TbItemDesc desc = tbItemDescMapper.selectByPrimaryKey(id);
		return desc;
	}

	/**
	 * 通过商品id查询param 信息(商品规格)
	 */
	@Override
	public TbItemParamItem findTbItemParamByItemId(Long id) {
		// TODO Auto-generated method stub
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(id);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExample(example);
		return list.get(0);
	}

	/**
	 * 添加商品并同步索引库
	 */
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource(name = "activeMQTopic")
	private Topic activeMQTopic;

	@Override
	public TaotaoResult addItem(TbItem iterm, String desc) {
		// TODO 调用添加商品的信息
		final Long ID = IDUtils.genItemId();
		iterm.setId(ID);
		// 1正常2下架3删除
		iterm.setStatus((byte) 1);
		iterm.setCreated(new Date());
		iterm.setUpdated(new Date());
		tbItemMapper.insert(iterm);
		TbItemDesc record = new TbItemDesc();
		record.setCreated(new Date());
		record.setItemDesc(desc);
		record.setItemId(iterm.getId());
		record.setUpdated(new Date());
		tbItemDescMapper.insert(record);
		// 添加商品同步索引
		jmsTemplate.send(activeMQTopic, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage textMessage = session.createTextMessage(ID.toString());
				return textMessage;
			}
		});

		return TaotaoResult.ok();
	}

}
