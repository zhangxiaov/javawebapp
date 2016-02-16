package webApp.http;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import webApp.http.servlet.HelloServlet;
import webApp.http.servlet.HomeServlet;
import webApp.http.servlet.StaticResourceServlet;

public class HttpConstants {

	public static String webappPath = null;
	public static Map<String, HttpServlet> apiMap = null;
	public static HttpServlet staticServlet = null;
	public static String charset = null;

	public static void init() {
		HttpConstants.apiMap();
		HttpConstants.appPath();
		HttpConstants.staticServlet();
		HttpConstants.charset();
	}

	/**
	 * 置app在系统中绝对路径
	 * 
	 */
	private static void appPath() {
		if (webappPath == null) {
			webappPath = HttpServer.class.getResource("/").getFile().toString();
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
