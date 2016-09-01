package com.taotao.cart.pojo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.taotao.pojo.TbItem;
/**
 * 返回页面结果的pojo
 * @author Mrlijing
 *
 */
public class Iterm extends TbItem implements Serializable{
	/**
	 * 添加获得图片信息的方法images属性
	 */
	public Iterm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String[] getImages(){
		//图片信息是一个逗号分隔的形式的需要拆分
		String image2 = this.getImage();
		if(StringUtils.isNotBlank(image2)) {
			return image2.split(",");
		}
		return null;
	}
	//使用有参构造初始化子类对象
	public Iterm(TbItem tbItem){
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
		this.setCreated(tbItem.getCreated());
		this.setId(tbItem.getId());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setTitle(tbItem.getTitle());
		this.setStatus(tbItem.getStatus());
		this.setUpdated(tbItem.getUpdated());
		this.setCid(tbItem.getCid());
	}

}
