package webApp.http;

import java.util.ArrayList;

import javax.servlet.http.Cookie;

public class SessionManager {
//	static Logger logger = Logger.getLogger(SessionManager.class);
	static String Id = "";
	static int number = 0;
	static ArrayList<Cookie> cookies = new ArrayList<Cookie>();
	static ArrayList<SessionCreateTime> session = new ArrayList<SessionCreateTime>();

	public static void addSession(Cookie cookie) {
		synchronized (cookies) {
			cookies.add(cookie);
		}
	}

	public static Cookie addSession() {
		clearCookie();
		synchronized (cookies) {
			number++;
			Id = "WebCat" + String.valueOf(number);
			Cookie cookieNew = new Cookie("jsessionid", Id);
			cookieNew.setDomain("127.0.0.1");
			cookieNew.setPath("/" + HttpConstants.appName);
			cookieNew.setMaxAge((int) HttpConstants.SESSIONMAXAGE * 60);
			addSession(cookieNew);
			synchronized (session) {
				SessionCreateTime SessionCreateTime = new SessionCreateTime();
				SessionCreateTime.setId(Id);
				SessionCreateTime.setCreateTime(System.currentTimeMillis());
				// ������ʱ���
				session.add(SessionCreateTime);
			}
//			logger.info("�½�session: " + Id);

			return cookieNew;

		}
	}

	public static Cookie getSession(String ID) {
		clearCookie();
		Cookie cooki = null;
		synchronized (cookies) {
			for (int i = 0; i < cookies.size(); i++) {
				Cookie cookie = cookies.get(i);
				if (cookie.getValue().equals(ID)) {
					cooki = cookie;
				}
			}
		}
		// return getSession();
		return cooki;
	}

	public static Cookie getSession() {// û���򴴽�һ��
		Cookie cookie = addSession();
		return cookie;
	}

	public static void clearCookies() {

		synchronized (cookies) {
			cookies.clear();
		}

	}

	public static void clearCookie() {
		synchronized (cookies) {
			for (int i = 0; i < cookies.size(); i++) {
				Cookie cookie = cookies.get(i);
				String Id = cookie.getValue();
				synchronized (session) {
					for (int j = 0; j < session.size(); j++) {
						if (Id.equals(session.get(j).getId())) {
							Long createTime = (Long) session.get(j)
									.getCreateTime();
							Long nowDate = System.currentTimeMillis();
							boolean isExpried = createTime + cookie.getMaxAge()
									* 1000L < nowDate;
							if (isExpried) {
								cookies.remove(i);
								session.remove(j);
//								logger.info("Session:" + Id + "��ʧЧ");
							}
						}
					}
				}

			}

		}
	}

	public static void updateCreateTime(String Id) {
		synchronized (session) {
			for (int j = 0; j < session.size(); j++) {
				if (Id.equals(session.get(j).getId())) {
					session.get(j).setCreateTime(System.currentTimeMillis());
				}
			}

		}

	}
}
