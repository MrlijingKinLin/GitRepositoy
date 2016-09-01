package activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveMQ {
	/**
	 * ActiveMQ生产者测试queue 一对一模式
	 * 
	 * @throws Exception
	 */
	@Test
	public void TestQueue() throws Exception {
		// 1.创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		// 2.创建连接对象
		Connection connection = connectionFactory.createConnection();
		// 3.打开连接
		connection.start();
		// 4.创建session 第一个参数开启事务 第二个参数 手动 自动
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.创建destination对象
		Queue queue = session.createQueue("item-id-queue");
		// 6.创建producer提供者
		MessageProducer producer = session.createProducer(queue);
		// 7.创建Text
		TextMessage textMessage = session.createTextMessage("消息队列");
		// 8.发送
		producer.send(textMessage);
		// 9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	/**
	 * ActiveMQ生产者测试queue 一对多模式
	 * 
	 * @throws Exception
	 */
	@Test
	public void TestActiveMQTopics() throws Exception {
		// 1.创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		// 2.使用连接工厂对象创建连接
		Connection connection = connectionFactory.createConnection();
		// 3.开启连接
		connection.start();
		// 4.创建session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.创建消息形式
		Topic topic = session.createTopic("item-id-topic");
		// 6.定义消息类型及内容
		TextMessage textMessage = session.createTextMessage("topic形式测试");
		// 7.创建生产者
		MessageProducer producer = session.createProducer(topic);
		// 8.发送消息
		producer.send(textMessage);
		// 9.关流释放资源
		producer.close();
		session.close();
		connection.close();
	}

	/**
	 * ActiveMQ测试 消费者测试 一对一模式
	 */
	@Test
	public void TestConsumerQueue() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		// 消息的应答模式 ,自动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("item-id-queue");
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				try {
					if (message instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) message;
						String string = textMessage.getText();
						System.out.println(string);
					}
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// 阻塞 方便测试, 等待键盘录入
		System.in.read();
		// 9.关流释放资源
		consumer.close();
		session.close();
		connection.close();
	}

	/**
	 * 消费者测试 一对多模式
	 */
	@Test
	public void TestConsumerTopic() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("item-id-topic");
		MessageConsumer consumer = session.createConsumer(topic);
		// 接收消息
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				try {
					if (message instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) message;
						String text = textMessage.getText();
						System.err.println(text);
					}
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		System.in.read();
		// 释放资源
		consumer.close();
		session.close();
		connection.close();
	}
}
