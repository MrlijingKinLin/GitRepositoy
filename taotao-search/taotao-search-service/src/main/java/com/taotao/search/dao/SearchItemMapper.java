package com.taotao.search.dao;

import java.util.List;

import com.taotao.common.pojo.SearchResult;

public interface SearchItemMapper {
	/**
	 * 添加商品索引到solr索引库
	 */
	public abstract List<SearchResult> addSearchIndex();
	/**
	 * 通过商品id查询商品信息,返回商品结果
	 * @param itemId
	 * @return
	 */
	public abstract SearchResult addSearchIndexByItemId(Long itemId);
	
}
