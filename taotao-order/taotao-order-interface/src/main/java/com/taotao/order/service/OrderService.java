package com.taotao.order.service;

import java.util.List;

import com.taotao.pojo.TbOrderItem;

public interface OrderService {
	/**根据商品id查询商品item信息
	 * @param itemId
	 * @return
	 */
	public abstract List<TbOrderItem> getTbOrderItemById(Long itemId);
}
