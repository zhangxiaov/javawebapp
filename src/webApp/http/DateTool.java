package webApp.http;

import java.util.*;
import java.text.*;

public class DateTool {


    public final static Locale LOCALE_CHINA = Locale.CHINA;

    //public final static TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT");
    public final static TimeZone GMT_ZONE = TimeZone.getDefault();

    public final static String OLD_COOKIE_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";

    public final static DateFormat oldCookieFormat =
        new SimpleDateFormat(OLD_COOKIE_PATTERN, LOCALE_CHINA);
    static {
        oldCookieFormat.setTimeZone(GMT_ZONE);
    }

}
