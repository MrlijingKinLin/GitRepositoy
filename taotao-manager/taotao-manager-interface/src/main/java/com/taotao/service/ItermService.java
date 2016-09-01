package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIPageResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;

public interface ItermService {
	/**
	 * 通过id查询商品
	 */
	abstract public TbItem findTBItemById(Long id);
	/**
	 * 查询所有商品
	 */
	abstract public List<TbItem> findTbItemByExample();
	/**
	 * 查询所有商品返回分页信息
	 * @param page
	 * @param rows
	 * @return
	 */
	public abstract EasyUIPageResult getEasyUIResult(int page, int rows);
	/**
	 * 添加商品
	 * @param iterm
	 * @param desc
	 * @return
	 */
	public abstract TaotaoResult addItem(TbItem iterm, String desc);
	/**
	 * 通过商品id查询商品描述详情
	 * @param id
	 * @return
	 */
	public abstract TbItemDesc findTBItemDescById(Long id);
	/**
	 * 通过id查询商品规格
	 * @param id
	 * @return
	 */
	public abstract TbItemParamItem findTbItemParamByItemId(Long id);
}
