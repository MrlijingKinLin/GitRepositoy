package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.dao.TbItemCatMapper;
import com.taotao.dao.TbItemDescMapper;
import com.taotao.dao.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	/**
	 * 根据父节点id查询节点信息
	 * 
	 * @see发布服务
	 */
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Override
	public List<Map>  getItemCatTreeResult(Long parentId) {
		// 根据id查询节点信息
		List<Map> catList = new ArrayList<>();
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		// 生成Tree控件返回格式 为 text id state
		for (TbItemCat tbItemCat : list) {
			Map map = new HashMap<>();
			map.put("id", tbItemCat.getId());
			map.put("text", tbItemCat.getName());
			// state 如果没有子节点 open 如果有closed
			map.put("state", tbItemCat.getIsParent() ? "closed" : "open");
			catList.add(map);
		}
		return catList;
	}
	
}
