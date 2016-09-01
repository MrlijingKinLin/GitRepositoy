package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.utils.TaotaoResult;

public interface SearchItemAddIndexService {
	/**
	 * 使用solrj 向索引库中添加商品索引
	 * @see需要显示的pojo对象
	 * @return
	 * @throws Exception 
	 */
	public abstract TaotaoResult addSearchIndex() throws Exception;
	/**
	 * 根据id查询,添加商品信息到索引库
	 */
	public abstract SearchResult addSearchIndexByItemId(Long itemId);
}
