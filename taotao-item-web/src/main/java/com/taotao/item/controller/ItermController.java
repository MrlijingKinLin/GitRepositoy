package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 商品信息展示controller
 * @author Mrlijing
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.item.pojo.Iterm;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItermService;
@Controller
public class ItermController {
	@Autowired
	private ItermService itermService;
	/*
	 * 返回逻辑视图/item/1039296.html
	 */
	@RequestMapping("/item/{id}")
	public String showItemInfo(@PathVariable Long id,Model model){
		TbItem tbItem = itermService.findTBItemById(id);
		Iterm item = new Iterm(tbItem);
		TbItemDesc itemDesc = itermService.findTBItemDescById(id);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
	
}
