<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding:10px 10px 10px 10px">
	<form id="contentAddForm" class="itemForm" method="post">
		
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addSearchIndex()">一键添加</a>
	</div>
</div>
<script type="text/javascript">

function addSearchIndex(){
	$.post("/search/addSearchIndex",null, function(data){
		if(data.status == 200){
			$.messager.alert('提示','添加索引库成功!');
		}else{
			$.messager.alert("提示","添加索引库失败!");
		}
	});
}
	
</script>
