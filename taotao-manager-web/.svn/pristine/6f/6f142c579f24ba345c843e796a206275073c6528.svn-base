package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 商品Controller层
 * @author Mrlijing
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIPageResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItermService;
@Controller
public class ItermController {
	/**
	 * 需要用到ItemsService没有加入对interface的依赖
	 */
	@Autowired
	private ItermService itermService;
	/**
	 * 查询所有商品(7800个商品这里需要使用分页插件)输入localhost:8081/直接访问
	 */
	@RequestMapping("/")
	public String getIndexPage(){
		return "index";
	}
	/**
	 * 根据页面请求返回对应的页面
	 */
	@RequestMapping("/{page}")
	public String getPages(@PathVariable String page){
		return page;
	}
	
	/**
	 * 根据页面的分页信息返回分页数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIPageResult getEasyUIPageResult(int page,int rows){
		//所有业务逻辑都应该放到service层来实现
		/*itermService.getEasyUIPageResult(page,rows);*/
		//设置分页信息
		EasyUIPageResult result = itermService.getEasyUIResult(page,rows);
		
		return result;
	}
	/**
	 * 商品编辑数据回显页面
	 */
	@RequestMapping("/rest/page/item-edit")
	public String updateItemUI(String[] ids,Model model){
		return "item-edit";
	}
	/**
	 * 商品描述
	 */
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public String getDesc(@PathVariable Long id){
		//通过id查询商品的描述
		TbItemDesc tbItemDesc = itermService.findTBItemDescById(id);
		return JsonUtils.objectToJson(tbItemDesc);
	}
	/**
	 * 商品规格表: tb_item_param
	 * @param patamdata
	 * @return data
	 */
	@RequestMapping("/rest/item/param/item/query/{id}")
	@ResponseBody
	public String getItem(@PathVariable Long id){
		//通过根据商品ID查询商品规格信息
		//返回值为data 对象 包含 status 200 data.paramData  data.id  使用map放入data pojo 然后放入状态的返回格式
		TbItemParamItem tbItemParamItem = itermService.findTbItemParamByItemId(id);
		return JsonUtils.objectToJson(tbItemParamItem);
	}
	/**
	 * 保存富文本信息
	 * @paramkindEditor
	 */
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult saveItem(TbItem iterm,String desc){
		//保存商品信息到数据库需要添加没有的字段的值
		TaotaoResult result = itermService.addItem(iterm,desc);
		return result;
	}
	
	/**
	 * 商品删除功能
	 */
	
	
	/**
	 * 商品上架
	 */
	
	
	/**
	 * 商品下架
	 */
}
