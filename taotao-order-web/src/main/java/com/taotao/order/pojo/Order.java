package com.taotao.order.pojo;

import java.io.Serializable;
import java.util.List;

import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

public class Order extends TbOrderShipping implements Serializable{
	List<TbOrderItem> orderItems;
	List<Iterm> cart;
	
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public List<Iterm> getCart() {
		return cart;
	}
	public void setCart(List<Iterm> cart) {
		this.cart = cart;
	}
}
