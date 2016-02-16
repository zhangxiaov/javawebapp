package webApp.http;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GenericResponseWrapper extends HttpServletResponseWrapper {
	private ByteArrayOutputStream output;
	private int contentLength;
	private String contentType;
	private PrintWriter pw;
	
	public GenericResponseWrapper(HttpServletResponse response) {
		super(response);
		output = new ByteArrayOutputStream();
	}

	public byte[] getData() {
		return output.toByteArray();
	}

	public ServletOutputStream getOutputStream() {
//		return new FilterServletOutputStream(output);
		return null;
	}

	public PrintWriter getWriter() {
		if(pw == null){
			pw = new PrintWriter(getOutputStream(), true);
		}
		return pw;
	}

	public int getContentLength() {
		return contentLength;
	}
}
