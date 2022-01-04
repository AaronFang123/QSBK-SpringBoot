package fun.aaronfang.qsbk.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String getDateToString(int time) {
        SimpleDateFormat sf;
        long newTime = time * 1000L;
        Date d = new Date(newTime);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }
}
