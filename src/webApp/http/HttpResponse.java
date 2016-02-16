package webApp.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpResponse implements HttpServletResponse {
	public ByteBuffer buffer = ByteBuffer.allocate(1024);
	PrintWriter out = null;

	public HttpResponse(HttpServletRequest request) {

		HttpProcess process = new HttpProcess();
		process.service(request, this);
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (this.out == null) {
			Writer w = new Writer() {

				@Override
				public void write(char[] cbuf, int off, int len)
						throws IOException {
					CharBuffer cb = CharBuffer.allocate(len);
					cb.put(cbuf, off, len);
					cb.flip();
					buffer.put(Charset.forName(HttpConstants.charset).encode(cb));
				}

				@Override
				public void flush() throws IOException {
					// TODO Auto-generated method stub

				}

				@Override
				public void close() throws IOException {
					// TODO Auto-generated method stub

				}
			};
			this.out = new PrintWriter(w);
		}

		return this.out;
	}

	@Override
	public void setCharacterEncoding(String charset) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentLength(int len) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentType(String type) {
		// TODO Auto-generated method stub

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

	}

	@Override
	public void setStatus(int sc, String sm) {
		// TODO Auto-generated method stub

	}
}
