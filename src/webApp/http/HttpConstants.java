package webApp.http;

import java.util.HashMap;
import java.util.Map;

import webApp.http.servlet.HelloServlet;
import webApp.http.servlet.HomeServlet;
import webApp.http.servlet.StaticResourceServlet;
import webApp.http.servlet.UploadFileServlet;
import webApp.http.servlet.WebServlet;

public class HttpConstants {

	public static String appName = null;
	public static String webappPath = null;
	
	public static Map<String, WebServlet> apiMap = null;
	public static Map<String, String> contentTypeMap = null;
	
	public static WebServlet staticServlet = null;
	public static String charset = null;
	public static  double SESSIONMAXAGE=30;
	
	public static String upload_image = null;
	
	public static void init() {
		HttpConstants.apiMap();
		HttpConstants.contentTypeMap();
		
		HttpConstants.appPath();
		HttpConstants.staticServlet();
		HttpConstants.charset();
		
		upload_image = webappPath + "/image/";
	}

	/**
	 * 置app在系统中绝对路径
	 * 
	 */
	private static void appPath() {
		if (webappPath == null) {
			webappPath = HttpServer.class.getResource("/").getFile().toString();
			int len = webappPath.length();
			if(webappPath.substring(len - 1).equals("/")) {
				webappPath = webappPath.substring(0, len - 1);
				System.out.println(webappPath);
			}
		}
	}

	/**
	 * 置处理api之servlet api即http header中url
	 */
	private static void apiMap() {
		if (apiMap == null) {
			apiMap = new HashMap<>();
			apiMap.put("/", new HomeServlet());
			apiMap.put("/hello", new HelloServlet());
			apiMap.put("/uploadFile", new UploadFileServlet());

		}
	}
	
	/**
	 * 
	 */
	private static void contentTypeMap() {
		if(contentTypeMap == null) {
			contentTypeMap = new HashMap<>();
//			contentTypeMap.put("ico", "image/x-icon");
			contentTypeMap.put("ico", "image/png");
			contentTypeMap.put("png", "image/png");
			contentTypeMap.put("html", "text/html");
			contentTypeMap.put("jpeg", "image/jpeg");
			contentTypeMap.put("css", "text/plain");
			contentTypeMap.put("js", "text/plain");
		}
	}

	/**
	 * 置处理静态servlet
	 */
	private static void staticServlet() {
		if (staticServlet == null)
			staticServlet = new StaticResourceServlet();
	}
	
	private static void charset() {
		charset = "utf-8";
	}
}
