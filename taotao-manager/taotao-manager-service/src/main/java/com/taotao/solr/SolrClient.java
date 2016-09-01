package com.taotao.solr;

/*
	id   商品id 索引  不分词 存储      
	title      索引  分   词  存储
	sell_point 索引 分词  存储
	price      索引 分词 存储
	image      不索引 不分词 存储
	cid        索引 不分词 存储
	desc       索引 分词 不存储
	lusen 的field域常用类型
	field类	 数据类型	是否分词	是否索引	是否存储
	StringField	字符串	N	Y 	Y/n
	LongField	Long	n	y	y/n
	StoredField	多种类型	n	n	y
	TextField	字符串或者流y	y	y/n	
	
	  设置分词域 先声明后使用
	  <>
	*/


