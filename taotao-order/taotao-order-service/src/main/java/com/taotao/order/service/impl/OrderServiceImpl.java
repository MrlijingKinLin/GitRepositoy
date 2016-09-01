package com.taotao.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.dao.TbOrderItemMapper;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderItemExample;
import com.taotao.pojo.TbOrderItemExample.Criteria;
@Service
public class OrderServiceImpl implements OrderService {
@Autowired
private TbOrderItemMapper tbOrderItemMapper;
	@Override
	public List<TbOrderItem> getTbOrderItemById(Long itemId) {
		// 查询订单item信息
		TbOrderItemExample example = new TbOrderItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId.toString());
		List<TbOrderItem> list = tbOrderItemMapper.selectByExample(example);
		return list;
	}

}
