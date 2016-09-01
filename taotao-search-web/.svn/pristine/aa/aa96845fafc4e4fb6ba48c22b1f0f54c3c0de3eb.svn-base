package com.taotao.searchcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.QuerySolrResult;
import com.taotao.search.service.SearchItemService;


/**
 * 商品搜索表现层
 * 
 * @author Mrlijing
 *
 */
@Controller
public class SearchItemController {
	@Value("${ROWS}")
	private Integer ROWS;
	@Autowired
	private SearchItemService searchItemService;
	@RequestMapping("/search")
	public String searchItems(@RequestParam(value="q")String queryString,@RequestParam(defaultValue="1")Integer page,Model model) throws Exception {
		
		queryString = new String(queryString.getBytes("iso8859-1"), "UTF-8");
		QuerySolrResult querySolrResult = searchItemService.querySolrResult(queryString, page, ROWS);
		model.addAttribute("totalPages", querySolrResult.getTotalPage());
		model.addAttribute("query", queryString);
		model.addAttribute("itemList", querySolrResult.getItems());
		model.addAttribute("page", page);
		//返回普通逻辑视图
		return "search";
	}
	
	
}
