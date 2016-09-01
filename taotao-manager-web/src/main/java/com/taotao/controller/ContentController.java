package com.taotao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIPageResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.content.service.TbContentService;
import com.taotao.pojo.TbContent;

@Controller
public class ContentController {
	@Autowired
	private TbContentService tbContentService;

	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIPageResult queryContentList(Long categoryId, Integer page, Integer rows) {
		EasyUIPageResult easyUIPageResult = tbContentService.getEasyUiResult(categoryId, page, rows);
		return easyUIPageResult;
	}
//Request URL:http://localhost:8081/content-add?_=1471793292341
	@RequestMapping("/content-add")
	public String addContentUI() {
		return "content-add";
	}
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content) {
		TaotaoResult result = tbContentService.addContent(content);
		return result;
	}

	@RequestMapping("/content-edit")
	@ResponseBody
	public TaotaoResult editContent(TbContent content) {
		TaotaoResult result = tbContentService.editContext(content);
		return result;
	}

	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContent(String ids) {
		List<String> list = new ArrayList<>();
		String[] strings = ids.split(",");
		for (String string : strings) {
			list.add(string);
		}
		TaotaoResult result = tbContentService.deleteContextByIds(list);
		return result;
	}

}
