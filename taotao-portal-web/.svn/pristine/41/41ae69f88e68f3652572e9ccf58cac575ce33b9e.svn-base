package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.TbContentService;
import com.taotao.pojo.TbContent;

import pojo.Ad1Node;

@Controller
public class ContentCatgoryController {
	@Autowired
	private TbContentService tbContentService;
	@Value("${AD1_CAT_ID}")
	private Long AD1_CAT_ID;
	@Value("${AD1_CAT_HEIGHT}")
	private Integer AD1_CAT_HEIGHT;
	@Value("${AD1_CAT_HEIGHTB}")
	private Integer AD1_CAT_HEIGHTB;
	@Value("${AD1_CAT_WIDTH}")
	private Integer AD1_CAT_WIDTH;
	@Value("${AD1_CAT_WIDTHB}")
	private Integer AD1_CAT_WIDTHB;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		// 通过id查询广告内容
		List<TbContent> list = tbContentService.getListByCatgoryId(AD1_CAT_ID);
		List<Ad1Node> ad1 = new ArrayList<>();
		// 属性文件中配置长宽高等信息
		// 遍历存入广告信息中
		for (TbContent tbContent : list) {
			Ad1Node ad = new Ad1Node();
			ad.setAlt(tbContent.getTitle());
			ad.setHeight(AD1_CAT_HEIGHT);
			ad.setHeightB(AD1_CAT_HEIGHTB);
			ad.setHref(tbContent.getUrl());
			ad.setSrc(tbContent.getPic());
			ad.setSrcB(tbContent.getPic2());
			ad.setWidth(AD1_CAT_WIDTH);
			ad.setWidthB(AD1_CAT_WIDTHB);
			ad1.add(ad);
		}
		model.addAttribute("ad1", JsonUtils.objectToJson(ad1));
		return "index";
	}
}
