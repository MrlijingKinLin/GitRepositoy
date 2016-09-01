package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIPageResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.content.service.TbContentService;
import com.taotao.dao.TbContentMapper;
import com.taotao.jedis.JedisClient;
import com.taotao.jedis.JedisClientPool;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

@Service
public class TbContentServiceImpl implements TbContentService {
	/**
	 * 使用redis缓存提高查询性能(集群版)
	 * 
	 * @see jedis存入key应该方便管理和查询 这里使用配置属性文件的方式来解决
	 */
	@Autowired
	private JedisClientPool jedisClientPool;
	@Autowired
	TbContentMapper tbContentMapper;
	@Value("${CONTENT_CATGORY_KEY}")
	private String CONTENT_CATGORY_KEY;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;

	@Override
	public List<TbContent> queryContentList(Long categoryId) {
		// 由于商品内容过多这里使用redis 80%的客户访问集中在20%的客户需求上使用缓存技术来解决
		// 使用缓存的原则是不应该影响实际业务所以需要try catch 处理自己的异常
		// 查询内容列表先从 缓存中获取内容列表,没有再去数据库查询
		try {
			String hget = jedisClientPool.hget(CONTENT_KEY, categoryId.toString());
			// 判断是否为空
			if (StringUtils.isNotBlank(hget)) {
				List<TbContent> list = JsonUtils.jsonToList(hget, TbContent.class);
				return list;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// 查询数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		// 将查询的数据存入缓存
		try {
			jedisClientPool.hset(CONTENT_KEY, categoryId.toString(), JsonUtils.objectToJson(list));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public TaotaoResult addContent(TbContent content) {
		// 添加内容列表
		/**
		 * @TODO 补全商品信息
		 * @param 补全商品信息
		 * 使用redis 添加后更新redis信息
		 */
		final String ID = content.getId().toString();
		content.setCreated(new Date());
		content.setUpdated(new Date());
		tbContentMapper.insert(content);
		// 缓存同步
		jedisClientPool.hdel(CONTENT_KEY, content.getCategoryId().toString());
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult editContext(TbContent content) {
		// 编辑商品内容列表
		// 使用redis 编辑后更新redis信息
		tbContentMapper.updateByPrimaryKey(content);
		// 缓存同步
		jedisClientPool.hdel(CONTENT_KEY, content.getCategoryId().toString());
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContextByIds(List<String> ids) {
		// 批量删除
		// 使用redis 删除后更新redis信息
		for (String id : ids) {
			tbContentMapper.deleteByPrimaryKey(Long.valueOf(id));
			// 缓存同步
			jedisClientPool.hdel(CONTENT_KEY, id);
		}

		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getListByCatgoryId(Long cid) {
		// TODO 查询商品信息用于广告位展示\
		// 使用redis
		try {
			
			String hget = jedisClientPool.hget(CONTENT_KEY, cid.toString());
			if(StringUtils.isNotBlank(hget)){
				List<TbContent> list = JsonUtils.jsonToList(hget, TbContent.class);
				return list;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		// 将查询的数据存入缓存
		try {
			jedisClientPool.hset(CONTENT_KEY, cid.toString(), JsonUtils.objectToJson(list));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public EasyUIPageResult getEasyUiResult(Long categoryId, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		EasyUIPageResult result = new EasyUIPageResult();
		PageHelper helper = new PageHelper();
		// 设置分页参数
		helper.startPage(page, rows);
		// 构建需要分页的数据
		List<TbContent> list = queryContentList(categoryId);
		// 获得分页内容
		PageInfo<TbContent> info = new PageInfo<>(list);
		result.setRows(list);
		result.setTotal((int) info.getTotal());
		return result;
	}

}
