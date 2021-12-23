package fun.aaronfang.qsbk.demo.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
public class Result  implements Serializable {
    /**
     * 业务状态码
     */
    public String msg;
    /**
     * 业务数据
     */
    public Object data;

    public int errorCode;

    Result(String msg, Object data) {
        this.msg = msg;

        if (data instanceof List) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("list", data);
            this.data = map;
        }
        else {
            this.data = data;
        }
    }

    Result(int errorCode, String msg, Object data) {
        this(msg, data);
        this.errorCode = errorCode;
    }

    public static Result buildResult() {
        return new Result("ok", "");
    }

    public static Result buildResult(String msg) {
        return new Result(msg, "");
    }

    public static Result buildResult(Object data) {
        return new Result("ok", data);
    }

    public static Result buildResult(String msg, Object data) {
        return new Result(msg, data);
    }

    public static Result buildResult(int errorCode, String msg, Object data) {
        return new Result(errorCode, msg, data);
    }
}
