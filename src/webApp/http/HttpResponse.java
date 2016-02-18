package webApp.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpResponse implements HttpServletResponse {
	public ByteBuffer messageBuffer = null;

	String charEncoding = null;
	String contentType = null;
	int contentLen = -1;
	HttpRequest request;
	int statusCode = 0;
	String encode = null;
	String sm = null;
	protected final SimpleDateFormat format = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

	protected ArrayList cookies = new ArrayList();

	public HttpResponse(HttpRequest request) {
		this.request = request;
		HttpProcess process = new HttpProcess();
		process.service(request, this);
	}

	public String getDate() {
		return format.format(new Date());
	}

	public int getContentLength() {
		// \r\n
		return contentLen;
	}

	public String assembleHeader() {
		StringBuffer header = new StringBuffer();
		header.append(request.getProtocol()).append(" ").append(getStatus());
		header.append(" ").append(this.sm).append("\r\n");
		header.append("Date: ").append(getDate()).append("\r\n");
		if (encode != null)
			header.append("Content-Encoding: ").append(encode).append("\r\n");
		if (contentType != null)
			header.append("Content-Type: ").append(getContentType())
					.append("\r\n");
		if (contentLen != -1)
			header.append("Content-Length: ").append(getContentLength())
					.append("\r\n");

//		String sessionId = request.getRequestedSessionId();
//		Cookie cook = SessionManager.getSession(sessionId);
//
//		if (request.isSecure())
//			cook.setSecure(true);
//		addCookie(cook);
//		// }
//		if (cook != null) {
//
//			synchronized (cookies) {
//				Iterator<Cookie> items = cookies.iterator();
//				while (items.hasNext()) {
//					Cookie cookie = (Cookie) items.next();
//					header.append(CookieTools.getCookieHeaderName(cookie));
//					header.append(": ");
//					header.append(CookieTools.getCookieHeaderValue(cookie));
//					header.append("\r\n");
//
//					header.append(CookieTools.getCookieHeaderName(cookie));
//					header.append(": ");
//					header.append(CookieTools.getCookieHeaderValue(cookie));
//					header.append("\r\n");
//				}
//			}
//		}

		header.append("\r\n"); //
		
		return header.toString();
	}

	public String getProtocol() {
		return request.getProtocol();
	}

	public int getStatus() {
		return this.statusCode;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return this.contentType;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		ServletOutputStream outp = new ServletOutputStream() {

			public void write(byte b[]) throws IOException {
				byte[] header = assembleHeader().getBytes();
				messageBuffer = ByteBuffer
						.allocate(header.length + b.length);
				messageBuffer.put(header);
				messageBuffer.put(b);

				messageBuffer.flip();
			}

			@Override
			public void write(int b) throws IOException {
				// TODO Auto-generated method stub

			}
		};
		return outp;
	}

	@Override
	public PrintWriter getWriter() throws IOException {

		return null;
	}

	@Override
	public void setCharacterEncoding(String charset) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentLength(int len) {
		// TODO Auto-generated method stub
		this.contentLen = len;
	}

	@Override
	public void setContentType(String type) {
		// TODO Auto-generated method stub
		this.contentType = type;
	}

	@Override
	public void setBufferSize(int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLocale(Locale loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCookie(Cookie cookie) {
		// TODO Auto-generated method stub
		synchronized (cookies) {
			cookies.add(cookie);
		}
	}

	@Override
	public boolean containsHeader(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String encodeURL(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeRedirectURL(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeRedirectUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendError(int sc) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendRedirect(String location) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDateHeader(String name, long date) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDateHeader(String name, long date) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHeader(String name, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addHeader(String name, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIntHeader(String name, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addIntHeader(String name, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStatus(int sc) {
		// TODO Auto-generated method stub
		this.statusCode = sc;
		switch (sc) {
		case SC_OK:
			sm = "OK";
			break;
		default:
			sm = "Unknow";
		}
	}

	@Override
	public void setStatus(int sc, String sm) {
		// TODO Auto-generated method stub
		switch (sc) {
		case SC_OK:
			sm = "OK";
		default:
			sm = "Unknow";
		}
	}
}
