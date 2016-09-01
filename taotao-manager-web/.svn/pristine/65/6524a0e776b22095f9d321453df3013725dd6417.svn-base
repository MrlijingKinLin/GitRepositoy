package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.search.service.SearchItemAddIndexService;

@Controller
public class SearchItermAddIndexController {
	@Autowired
	private SearchItemAddIndexService searchItemAddIndexService;

	@RequestMapping("/search/addSearchIndex")
	@ResponseBody
	public TaotaoResult addSerachIndex() throws Exception {
		TaotaoResult result = searchItemAddIndexService.addSearchIndex();
		return result;
	}
}
