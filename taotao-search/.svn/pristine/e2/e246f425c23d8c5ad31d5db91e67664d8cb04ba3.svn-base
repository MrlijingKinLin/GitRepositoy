package com.taotao.search.service;

import com.taotao.common.pojo.QuerySolrResult;
import com.taotao.common.utils.TaotaoResult;

public interface SearchItemService {
	/**
	 * 门户查询服务
	 * @param queryString 查询的字符串
	 * @param page 当前页
	 * @param rows 每页记录条数
	 * @return 带有分页信息的结果pojo
	 * @throws Exception 
	 */
	public abstract QuerySolrResult querySolrResult(String queryString,Integer page,Integer rows) throws Exception;
	/**
	 * 通过id更新索引库
	 * @param id
	 * @return
	 * @throws Exception 
	 * @throws SolrServerException 
	 */
	public abstract TaotaoResult addItemById(Long id) throws Exception;
}
