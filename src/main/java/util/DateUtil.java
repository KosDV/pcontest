package util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date getCurrentDate() {
	return Calendar.getInstance().getTime();
    }
}