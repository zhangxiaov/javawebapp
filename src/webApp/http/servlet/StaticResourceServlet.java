package webApp.http.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import webApp.http.HttpConstants;
import webApp.http.HttpRequest;
import webApp.http.HttpResponse;

public class StaticResourceServlet extends WebServlet{

	public void service(HttpRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String resourcePath = HttpConstants.webappPath + request.getContextPath();

		byte data[] = null;
		try {
			FileInputStream fis = new FileInputStream(resourcePath);
			int size = fis.available(); // 得到文件大小
			data = new byte[size];
			fis.read(data); // 读数据
			fis.close();
			
			int index = resourcePath.lastIndexOf(".");
			String suffix = resourcePath.substring(index+1);
		
			response.setStatus(HttpResponse.SC_OK);
			response.setContentType(HttpConstants.contentTypeMap.get(suffix));
			
		} catch (FileNotFoundException e) {
			data = "Sorry,no this page\r\n".getBytes();
			response.setStatus(HttpResponse.SC_NOT_FOUND);
			response.setContentType("text/plain"); // 设置返回的文件类型
		}

		response.setContentLength(data.length);
		
//		System.out.println("data len" +data.length);
		
		response.getOutputStream().write(data);
	}
	
}
