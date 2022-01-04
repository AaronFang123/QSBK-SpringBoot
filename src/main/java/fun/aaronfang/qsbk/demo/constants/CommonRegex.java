package fun.aaronfang.qsbk.demo.constants;

public class CommonRegex {

    /**
     * 密码规则
     */
    public static final String PASSWORD_REGEX ="^\\w{3,20}$";

    public static boolean isCheckPwd(String str){
        return str.matches(PASSWORD_REGEX);
    }

}
