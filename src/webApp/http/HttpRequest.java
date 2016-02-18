package webApp.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpRequest implements HttpServletRequest {
	public HttpHead head = null;
	public Map<String, String> paramsMap = null;

	public Map<String, byte[]> byteParamsMap = null;

	public byte[] bytes = null;

	public HttpRequest(byte[] bs) {
		head = new HttpHead();
		paramsMap = new HashMap<>();
		byteParamsMap = new HashMap<>();
		
		this.bytes = bs;
	}

	public void parseMessageIn() {
		if (bytes == null)
			return;

		ByteReader br = new ByteReader(bytes);
		byte[] methodLine = br.readLine();
		if(methodLine == null || methodLine.length == 0)
			return;
		
		String[] methods = ByteReader.split(methodLine, ' ');
		head.method = methods[0];
		head.requestURL = methods[1];
		head.protocol = methods[2];

//		System.out.println("method " + head.method);
//		System.out.println("url " + head.requestURL);
//		System.out.println("protocol" + head.protocol);
		
		for (;;) {
			byte[] bs = br.readLine();
			
			syso
			
			if (bs == null || bs.length == 0) {
				break;
			} else {
				String[] kv = ByteReader.kvSplit(bs, ':');
				head.kv.put(kv[0], kv[1]);
			}
		}// head end

		// init params
		if (methods[0].equals("GET")) {
			int index = methods[1].indexOf("?");
			if (index != -1) {
				head.requestURL = methods[1].substring(0, index);
				String[] kvs = methods[1].substring(index+1).split("&");
				for (String string : kvs) {
					String[] kv = string.split("=");
					paramsMap.put(kv[0], kv[1]);
				}
			}
		} else if (methods[0].equals("POST")) {
			String c_type = head.kv.get("Content-Type");
			int index = c_type.indexOf("boundary=");
			if (index == -1) {
				String content = new String(br.readLine());
				String[] kvs = content.split("&");
				for (String string : kvs) {
					String[] kv = string.split("=");
					paramsMap.put(kv[0], kv[1]);
				}
			} else {
				/**
				 * 过滤boundary下形如 Content-Disposition: form-data; name="bin";
				 * filename="12.pdf" Content-Type: application/octet-stream
				 * Content-Transfer-Encoding: binary 的字符串
				 */
				index += "boundary=".length();
				String boundary = "--" + c_type.substring(index);
				String lastboundary = boundary + "--";

				
				byte[] bs = br.readLine();;
				
				// 循环解析文件
				//--OCqxMF6-JxtxoMDHmoG5W5eY9MGRsTBp
				// Content-Disposition: form-data; name="lng"
				// Content-Type: text/plain; charset=UTF-8
				// Content-Transfer-Encoding: 8bit
				// 116.361545
				while (bs != null) {
					if(bs.length <= 0) {
						bs = br.readLine();
						continue;
					}
					
					String[] kvs = ByteReader.split(bs, ';');

					String name_val = null;
					for (String string : kvs) {
						String[] kv = string.split("=");

						if (kv[0].equals("name")) {
							name_val = kv[1];
							bs = br.readLine();
							while ((bs = br.readLine()).length > 0)
								;
							// \r\n
							// content
							bs = br.readLine();
							byteParamsMap.put(name_val, bs);

						} else if (kv[0].equals("filename")) {
							String fileNameKey = name_val + "_filename";
							paramsMap.put(fileNameKey, kv[1]);
						}
					}

					byte[] boundary_t = br.readLine();
					if (ByteReader.indexOf(lastboundary.getBytes(), boundary_t) >= 0) {
						break;
					}
				}
				
				bs = br.readLine();
			}
		}
		
		
//		System.out.println("name "+ paramsMap.get("userName"));
	}

	@Override
	public Object getAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return paramsMap.get("Content-Encoding");
	}

	@Override
	public int getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameter(String arg0) {
		// TODO Auto-generated method stub
		return paramsMap.get(arg0);
	}

	@Override
	public Map getParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParameterValues(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return head.protocol;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRealPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return paramsMap.get("Host");
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return head.requestURL;
	}

	@Override
	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getDateHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHeader(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getHeaders(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletPath() {
		// TODO Auto-generated method stub
		// 绝对路径
		return null;
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	// public static void main(String[] args) {
	// String content = "hello/MyServlet";
	//
	// System.out.println(content.substring(0, content.indexOf("/")));
	// System.out.println(content.substring(content.indexOf("/") + 1,
	// content.length()));
	// }

}
