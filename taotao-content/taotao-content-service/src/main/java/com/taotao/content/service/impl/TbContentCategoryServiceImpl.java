package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUiTreeResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.content.service.TbContentCategoryService;
import com.taotao.dao.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EasyUiTreeResult> getEasyUiTreeResult(Long parentId) {
		// TODO Auto-generated method stub
		List<EasyUiTreeResult> result = new ArrayList<>();
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);

		for (TbContentCategory tbContentCategory : list) {
			EasyUiTreeResult easyUiTreeResult = new EasyUiTreeResult();
			easyUiTreeResult.setId(tbContentCategory.getId());
			easyUiTreeResult.setText(tbContentCategory.getName());
			easyUiTreeResult.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			result.add(easyUiTreeResult);
		}
		return result;
	}

	@Override
	public TaotaoResult createNodeById(Long parentId, String text) {
		// TODO Auto-generated method stub完善新增商品信息
		TbContentCategory tbContentCategory = new TbContentCategory();
		// 创建时间
		tbContentCategory.setCreated(new Date());
		// 商品id自动增长不需要维护,需要返回主键
		// 是否是父节点,新建的节点,是叶子节点
		tbContentCategory.setIsParent(false);
		// 设置分类名称
		tbContentCategory.setName(text);
		// 设置父节点id
		tbContentCategory.setParentId(parentId);
		// 排序字段默认是1
		tbContentCategory.setSortOrder(1);
		// s分类状态 1: 正常 2: 删除
		tbContentCategory.setStatus(1);
		// 更新时间
		tbContentCategory.setUpdated(new Date());
		// 保存商品分类信息
		tbContentCategoryMapper.insert(tbContentCategory);
		// 逻辑 增加后修改父节点的状态 如果之前有叶子节点不用修改,如果之前没有叶子节点修改isParent true
		// 根据父节点查询商品分类信息
		TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if (!contentCategory.getIsParent()) {
			contentCategory.setIsParent(true);
			contentCategory.setUpdated(new Date());
			tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
		}
		// mapper.xml中设置主键返回
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult updateCatgoryNode(Long id, String text) {
		// 根据id查询当前分类信息
		TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		// 修改信息
		contentCategory.setName(text);
		contentCategory.setUpdated(new Date());
		// 保存修改后的结果
		tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
		// 返回结果
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteCategoryNode(Long id) {
		// 根据id查询当前商品分类信息
		TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		// 查询父节点信息
		if (!contentCategory.getIsParent()) {
			Long parentId = contentCategory.getParentId();
			//如果不是父节点直接删除
			tbContentCategoryMapper.deleteByPrimaryKey(id);
			// 删除后根据parentId查询父类所有子类
			if (parentId != null) {
				TbContentCategory parentContentCategory = tbContentCategoryMapper
						.selectByPrimaryKey(parentId);
				TbContentCategoryExample example = new TbContentCategoryExample();
				Criteria criteria = example.createCriteria();
				criteria.andParentIdEqualTo(contentCategory.getParentId());
				List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
				// 如果size为0说明没有子节点,当前父节点修改为非
				if (list.size() == 0) {
					parentContentCategory.setIsParent(false);
				}
				return TaotaoResult.ok();
			}else {
				return TaotaoResult.build(200, "当前节点是父节点,请先删除子节点!");
			}
		}
		return null;
		
	}

}
