package fun.aaronfang.qsbk.demo.common;

public class ApiValidationException extends RuntimeException{
    int errCode;
    String msg;
    Object data;

    public ApiValidationException(String msg, int errCode, Object data) {
        super();
        this.errCode = errCode;
        this.msg = msg;
        this.data = data;
    }

    public ApiValidationException(String msg, int errCode) {
        super();
        this.errCode = errCode;
        this.msg = msg;
        this.data = null;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
