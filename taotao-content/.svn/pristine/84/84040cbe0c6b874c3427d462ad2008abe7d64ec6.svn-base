package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIPageResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * 商品内容管理
 * @author Mrlijing
 *
 */
public interface TbContentService {
	/**
	 * 查询商品内容列表
	 * @param categoryId
	 * @return
	 */
	public abstract List<TbContent> queryContentList(Long categoryId);
	/**
	 * 添加商品内容
	 * @param content
	 * @return
	 */
	public abstract TaotaoResult addContent(TbContent content);
	/**
	 * 编辑商品
	 * @param id
	 * @return
	 */
	public abstract TaotaoResult editContext(TbContent content);
	/**
	 * 批量删除商品信息
	 * @param ids
	 * @return
	 */
	public abstract TaotaoResult deleteContextByIds(List<String> ids);
	/**
	 * 根据商品类别查询所有商品信息
	 * @param cid
	 * @return
	 */
	public abstract List<TbContent> getListByCatgoryId(Long cid);
	/**
	 * 返回商品内容的分页信息
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	public abstract EasyUIPageResult getEasyUiResult(Long categoryId, Integer page, Integer rows);
}
