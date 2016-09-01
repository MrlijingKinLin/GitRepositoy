import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest {
	@Test
	public void TestFreeMarker() throws Exception{
		//1.创建一个configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		//2.设置模板文件所在路径
		configuration.setDirectoryForTemplateLoading(new File("F:/project/taotao/taotao-item-web/src/main/webapp/WEB-INF/ftl"));
		//3.设置模板文件使用的字符集
		configuration.setDefaultEncoding("UTF-8");
		//4.加载一个模板,创建一个模板对象
		Template template = configuration.getTemplate("test.ftl");
		//5.创建一个模板使用的数据集可有是pojo也可以是map
		Map data = new HashMap();
		
		data.put("hello","1");
		data.put("hello","2");
		data.put("hello","3");
		data.put("hello","4");
		//6.创建一个writer对象,一般创建一个 filewriter对象,指定生成的文件名
		Writer out = new FileWriter(new File("F:/img/hello.html"));
		//7.调用模板的process方法输出文件
		template.process(data, out);
		//8.关闭流
		out.close();
		
	}
}
