package webApp.http.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import webApp.http.HttpConstants;
import webApp.http.HttpRequest;

public class UploadFileServlet extends WebServlet {

	public void service(HttpRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("############");

		byte[] data = request.byteParamsMap.get("UpLoadFile");
		
		System.out.println(data.length);
		
		String clienFilePath = request.paramsMap.get("UpLoadFile_filename");

		int index = clienFilePath.lastIndexOf(".");
		String suffix = clienFilePath.substring(index);

		SimpleDateFormat f = new SimpleDateFormat("yyyyHHmmss");
		String path = HttpConstants.webappPath + "/" + f.format(new Date())
				+ suffix;

		File file = new File(path);

		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("clienFilePath " + clienFilePath);

	}
}
