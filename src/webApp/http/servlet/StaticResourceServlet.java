package webApp.http.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticResourceServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String resourcePath = request.getServletPath();
		System.out.println(resourcePath);

		byte data[] = null;
		try {
			FileInputStream fis = new FileInputStream(resourcePath);
			int size = fis.available(); // 得到文件大小
			data = new byte[size];
			fis.read(data); // 读数据
			fis.close();
		} catch (FileNotFoundException e) {
			data = "Sorry,no this page".getBytes();
		}

		response.setContentLength(data.length);
		response.setDateHeader("Date", 0L);
		response.setContentType("text/plain"); // 设置返回的文件类型
		// OutputStream os = response.getOutputStream();
		// os.write(data);
		// os.flush();
		// os.close();
		PrintWriter out = response.getWriter();
		out.print(data);
	}
	
}
