package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUiTreeResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.content.service.TbContentCategoryService;

@Controller
public class ContentCatgoryController {
	/**
	 * 商品类别服务
	 */
	@Autowired
	private TbContentCategoryService tbContentCategoryService;
	@ResponseBody
	@RequestMapping("/content/category/list")
	public List<EasyUiTreeResult> getCatgoryResult(@RequestParam(value="id",defaultValue="0") Long parentId){
		List<EasyUiTreeResult> result = tbContentCategoryService.getEasyUiTreeResult(parentId);
		return result;
	}
	
	/**
	 * 新增商品类别
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult createNode(Long parentId,String name){
		TaotaoResult result= tbContentCategoryService.createNodeById(parentId, name);
		return  result;
	}
	/**
	 * 更新商品类别
	 * @param id
	 * @param text
	 * @return
	 */
	@RequestMapping("/content/category/update")
	public TaotaoResult updateNode(Long id,String name){
		TaotaoResult result = tbContentCategoryService.updateCatgoryNode(id,name);
		return result;
	}
	/**
	 * 删除商品类别信息
	 * @param parentId
	 * @param id
	 * @return
	 */
	@RequestMapping("/content/category/delete")
	public TaotaoResult deleteCategoryNode(Long id){
		TaotaoResult result = tbContentCategoryService.deleteCategoryNode(id);
		return result;
	}
}
