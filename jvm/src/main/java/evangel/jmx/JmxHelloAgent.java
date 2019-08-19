package evangel.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import evangel.jmx.service.JmxHello;
import evangel.jmx.service.JmxJack;
import evangel.jmx.util.JmxUtil;

//import com.sun.jdmk.comm.HtmlAdaptorServer;
public class JmxHelloAgent {
	public static void main(String[] args) throws JMException, Exception {
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		ObjectName helloName = new ObjectName(JmxUtil.OBJECT_NAME);
		// create mbean and register mbean
		server.registerMBean(new JmxHello(), helloName);
		// Thread.sleep(60 * 60 * 1000);
		// # No.2
		// ObjectName adapterName = new
		// ObjectName("HelloAgent:name=htmladapter,port=8082");
		// HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		// server.registerMBean(adapter, adapterName);
		// adapter.start();
		broadcastNotification(server);
		// # No.3
		try {
			// 这个步骤很重要，注册一个端口，绑定url后用于客户端通过rmi方式连接JMXConnectorServer
			LocateRegistry.createRegistry(JmxUtil.JMX_PORT);
			// URL路径的结尾可以随意指定，但如果需要用Jconsole来进行连接，则必须使用jmxrmi
			JMXServiceURL url = new JMXServiceURL(
					"service:jmx:rmi:///jndi/rmi://localhost:"
							+ JmxUtil.JMX_PORT + "/jmxrmi");
			JMXConnectorServer jcs = JMXConnectorServerFactory
					.newJMXConnectorServer(url, null, server);
			jcs.start();
			System.out.println("rmi start...");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void broadcastNotification(MBeanServer server)
			throws Exception {
		ObjectName smithName = new ObjectName("smith:name=jmxHello");
		JmxHello smith = new JmxHello("Smith");
		server.registerMBean(smith, smithName);
		JmxJack jack = new JmxJack();
		server.registerMBean(jack, new ObjectName("jack:name=jmxJack"));
		jack.addNotificationListener(new HelloListener(), null, smith);
	}
}