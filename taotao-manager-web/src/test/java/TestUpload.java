

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
public class TestUpload {
/**
 * 图片上传测试类
 */
	@Test
	public void justtest() throws Exception{
		//图片上传的7大步骤
		//1.加载配置文件,配置文件中的内容是tracker服务的地址配置文件内容tracker_server = 192.168.133:22122
		ClientGlobal.init("F:/project/taotao/taotao-manager-web/src/main/resources/resource/client.conf");
		//2.创建一个TrackerClient对象直接new
		TrackerClient trackerClient = new TrackerClient();
		//3.使用TrackerClient对象创建连接,获得一个trackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//4.创建一个storageServer的引用值为null
		StorageServer storageServer = null;
		//5.创建一个storageClient对象,需要两个参数Trackerserver 和 storageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//6.使用storageClient上传图片
		/*NameValuePair nameValuePair = new NameValuePair();
		nameValuePair.setName("001.jpg");
		nameValuePair.setValue("这是个啥");*/
		String[] file = storageClient.upload_appender_file("F:/img/001.jpg", "jpg", null);
		//7.返回数组包含组名和图片的路径
		for (String string : file) {
			System.out.println(string);
		}
		//输出内容为group1
		//M00/00/00/wKgZhVe4O2CEBdeNAAAAAKKuxeY265.jpg
		//浏览器访问路径为http://192.168.25.133/group1/M00/00/00/wKgZhVe4O2CEBdeNAAAAAKKuxeY265.jpg
	}
}
