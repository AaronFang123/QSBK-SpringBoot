package fun.aaronfang.qsbk.demo.util;


import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class PhoneValidationUtil {
    private final static String str="^[1][3,5,7,8][0-9]\\d{8}$";
    private static final Pattern PATTERN= Pattern.compile(str);
    public static boolean isPhone(String phoneNum) {
        if (!StringUtils.hasLength(phoneNum)) {
            return false;
        } else {
            return PATTERN.matcher(phoneNum).matches();
        }
    }
}

