package org.su18.server;

import org.su18.utils.Logger;
import org.su18.utils.StringUtil;

import java.net.*;
import java.util.Enumeration;

public class ServerStart {

	public static String addr = getLocalIpByNetCard();

	public static int rmiPort = Integer.parseInt(StringUtil.getCurrentPropertiesValue("rmi.port"));

	public static int ldapPort = Integer.parseInt(StringUtil.getCurrentPropertiesValue("ldap.port"));

	private static int jettyPort = Integer.parseInt(StringUtil.getCurrentPropertiesValue("jettyPort.port"));

	private JettyServer jettyServer;

	private RMIServer rmiServer;

	private LDAPServer ldapServer;

	public ServerStart(URL codebase) throws Exception {

		jettyServer = new JettyServer(jettyPort);
		rmiServer = new RMIServer(rmiPort, codebase);
		ldapServer = new LDAPServer(ldapPort, codebase);
	}

	/**
	 * 直接根据第一个网卡地址作为其内网ipv4地址
	 *
	 * @return 返回 ip 地址
	 */
	public static String getLocalIpByNetCard() {
		try {
			for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements(); ) {
				NetworkInterface item = e.nextElement();
				for (InterfaceAddress address : item.getInterfaceAddresses()) {
					if (item.isLoopback() || !item.isUp()) {
						continue;
					}
					if (address.getAddress() instanceof Inet4Address) {
						Inet4Address inet4Address = (Inet4Address) address.getAddress();
						return inet4Address.getHostAddress();
					}
				}
			}
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public static void main(String[] args) throws Exception {

		ServerStart servers = new ServerStart(new URL("http://" + addr + ":" + jettyPort + "/"));
		Class.forName("org.su18.utils.Mapper");

		Logger.print("-------------------------- 服务端日志 ---------------------------");
		Logger.info("JETTY 服务器启动 >> 监听地址：0.0.0.0:" + jettyPort);
		Thread threadJetty = new Thread(servers.jettyServer);
		threadJetty.start();

		Logger.info(" RMI  服务器启动 >> 监听地址：0.0.0.0:" + rmiPort);
		Thread threadRMI = new Thread(servers.rmiServer);
		threadRMI.start();

		Logger.info("LDAP  服务器启动 >> 监听地址：0.0.0.0:" + ldapPort);
		Thread threadLDAP = new Thread(servers.ldapServer);
		threadLDAP.start();

	}
}
