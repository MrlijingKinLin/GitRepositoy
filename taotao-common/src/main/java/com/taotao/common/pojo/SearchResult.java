package com.taotao.common.pojo;

import java.io.Serializable;

/**
 * 搜索服务的封装实体类
 * 
 * @author Mrlijing
 *
 */
public class SearchResult implements Serializable {
	/**
	 * 商品搜索索引列表
	 */
	private String id;//商品id
	private String title;//商品标题
	private String sellPoint;//商品卖点
	private Long price;//商品价格
	private String image;//商品图片
	private String catgory;//商品类别
	private String itemDesc;//商品描述
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCatgory() {
		return catgory;
	}
	public void setCatgory(String catgory) {
		this.catgory = catgory;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
}
