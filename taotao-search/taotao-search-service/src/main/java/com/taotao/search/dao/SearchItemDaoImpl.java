package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * 查询数据实现类,只有一个不需要定义接口
 * @author Mrlijing
 *
 */

import com.sun.tools.javac.jvm.Items;
import com.taotao.common.pojo.QuerySolrResult;
import com.taotao.common.pojo.SearchResult;

@Repository
public class SearchItemDaoImpl {
	@Autowired
	private SolrServer solrServer;

	public QuerySolrResult querySolrResult(SolrQuery solrQuery) throws Exception {
		// 执行查询,获得查询结果集
		QueryResponse response = solrServer.query(solrQuery);
		// 获得查询总记录数
		SolrDocumentList solrDocumentList = response.getResults();
		long totalRecord = solrDocumentList.getNumFound();
		// queryResult设置总记录数
		QuerySolrResult querySolrResult = new QuerySolrResult();
		querySolrResult.setTotalRecord(totalRecord);
		// 创建结果集封装对象
		List<SearchResult> items = new ArrayList<>();
		// 遍历结果集,封装到对象和list中
		for (SolrDocument solrDocument : solrDocumentList) {
			// 获取高亮显示字段
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
			List<String> list = map.get(solrDocument.get(solrDocument.get("iterm_title")));
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}

			String id = (String) solrDocument.get("id");
			String sellPoint = (String) solrDocument.get("item_sell_point");
			Long price = (Long) solrDocument.get("item_price");
			String image = (String) solrDocument.get("item_image");
			if(StringUtils.isNotBlank(image)){
				String[] split = image.split(",");
				image=split[0];
			}
			String catgory = (String) solrDocument.get("item_category_name");
			// 商品描述???可以搜索不能显示 没存储
			String itemDesc = (String) solrDocument.get("item_desc");
			// 封装
			SearchResult seResult = new SearchResult();
			seResult.setCatgory(catgory);
			seResult.setId(id);
			seResult.setImage(image);
			seResult.setItemDesc(itemDesc);
			seResult.setPrice(price);
			seResult.setSellPoint(sellPoint);
			seResult.setTitle(title);
			items.add(seResult);
		}
		querySolrResult.setItems(items);
		return querySolrResult;
	}

	
}
