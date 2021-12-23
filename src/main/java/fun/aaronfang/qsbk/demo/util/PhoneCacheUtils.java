package fun.aaronfang.qsbk.demo.util;

import java.util.Random;

public class PhoneCacheUtils {

    public static String getPhoneCachedKey(String phoneNum) {
        return "vali_code" + phoneNum;
    }

    public static String genarateValiCode(String phone) {
        return String.format("%04d",new Random().nextInt(9999));
    }
}
