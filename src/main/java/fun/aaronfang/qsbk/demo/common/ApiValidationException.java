package fun.aaronfang.qsbk.demo.common;

public class ApiValidationException extends RuntimeException{
    int errCode;
    String msg;

    public ApiValidationException(String msg, int errCode) {
        super();
        this.errCode = errCode;
        this.msg = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getMsg() {
        return msg;
    }
}
