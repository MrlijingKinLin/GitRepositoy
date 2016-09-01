package com.taotao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FastDFSClient;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemCatService;

@Controller
public class ItemCatController {
	/**
	 * 通过id查询子节点信息
	 * @author Mrlijing
	 */
	@Autowired
	private ItemCatService itemCatService;
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public  List<Map> findItemCatByParentId(@RequestParam(defaultValue="0",value="id") Long parentId){
		 List<Map> list = itemCatService.getItemCatTreeResult(parentId);
		return list;
	}
	
	/***
	 * 
	 * 文件上传 页面参数 filePostName  : "uploadFile",
		//指定上传文件请求的url。
		uploadJson : '/pic/upload',@param
		//上传类型，分别为image、flash、media、file
		dir : "image"
		文件上传组件 依赖
	 */
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	@ResponseBody
	@RequestMapping("/pic/upload")
	public String fileUpload(MultipartFile uploadFile){
		try {
			//业务逻辑
			//1.接收页面传递的图片信息uploadFile
			//取文件的扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			//2.把图片上传到图片服务器,使用封装的工具类实现,需要去文件中的内容和文件名
			//创建fastDFS客户端
			FastDFSClient dfsClient = new FastDFSClient("classpath:resource/client.conf");
			String file = dfsClient.uploadFile(uploadFile.getBytes(), extName);
			//3.图片服务器返回图片/taotao-manager-web/src/main/resources/resources/client.conf
			//4.将图片的url补充完整,返回完整的url
			String url = IMAGE_SERVER_URL+file;
			System.out.println(url);
			//5.将结果封装到map中返回
			Map map = new HashMap<>();
			map.put("error", 0);
			map.put("url", url);
			String string = JsonUtils.objectToJson(map);
			return string;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap<>();
			map.put("error", 1);
			map.put("message", "图片上传失败");
			return JsonUtils.objectToJson(map);
			
		}
	}
	

}
