package webApp.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpProcess {
	public void service(HttpServletRequest request,HttpServletResponse response){  
	        String api = request.getContextPath();   

	        System.out.println("api:"+api);
	        
	        HttpServlet servlet = HttpConstants.apiMap.get(api);
	        try {
	        	 if (servlet == null) {
	 				//静态 html 图片 css js等
	 	        	HttpConstants.staticServlet.service(request, response);
	 			}else {
	 				//动态接口
					servlet.service(request, response);
	 			}
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }  
}
