package webApp.http;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class Session implements HttpSession {

//	static Logger logger=Logger.getLogger(WebCatSession.class);
	HttpRequest httpRequest;
	Cookie cookie;
	long creationTime;

	public Session(HttpRequest httpRequest) {
		this.httpRequest=httpRequest;
		this.creationTime=System.currentTimeMillis();
	}
	public Session(HttpRequest httpRequest,Cookie cookie) {
		this.httpRequest=httpRequest;
		this.cookie=cookie;
		this.creationTime=System.currentTimeMillis();
	}
	public Session(Cookie cookie) {
		this.cookie=cookie;
		this.creationTime=System.currentTimeMillis();
	}

	@Override
	public Object getAttribute(String name) {
//		return httpRequest.getCookie(name);
		return null;
	}
	@Override
	public void setAttribute(String name, Object value) {
		Cookie cookie= new Cookie(name,(String) value);
//		httpRequest.addCookie(cookie);
//		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getCreationTime() {
		return creationTime;
	}

	@Override
	public String getId() {
/*		if(cookie != null){
		String sessionId = cookie.getValue();
		return sessionId;
		}else{
			return "";
		}*/
		return httpRequest.getRequestedSessionId();
	}

	@Override
	public long getLastAccessedTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxInactiveInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getValueNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isNew() {
		return true;
	}

	@Override
	public void putValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeValue(String arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	public void setMaxInactiveInterval(int arg0) {
	

	}

}
