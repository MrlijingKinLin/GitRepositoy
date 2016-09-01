package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUiTreeResult;
import com.taotao.common.utils.TaotaoResult;

public interface TbContentCategoryService {
	/**
	 * 根据id查询 商品内容类别
	 * @param parentId
	 * @return
	 */
	List<EasyUiTreeResult> getEasyUiTreeResult(Long parentId);
	/**
	 * 新增商品内容分类
	 * @param parentId
	 * @param text
	 * @return
	 */
	TaotaoResult createNodeById(Long parentId,String text);
	/**
	 * 修改商品内容分类
	 * @param id
	 * @param text
	 * @return
	 */
	TaotaoResult updateCatgoryNode(Long id, String text);
	/**
	 * 删除商品分类
	 * @param parentId
	 * @param id
	 * @return
	 */
	TaotaoResult deleteCategoryNode(Long id);
	
	
}
