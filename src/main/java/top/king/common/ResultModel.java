package top.king.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultModel<T> implements Serializable {
    /**
     * 成功
     */
    public static String SUCCESS = "0";
    /**
     * 失败
     */
    public static String ERROR = "1";
    private String code = "";
    private Boolean result = true;
    private String msg = "操作成功！";
    private T data;
    private Long count = -1L;

    public ResultModel() {
        this.code = SUCCESS;
    }

    public ResultModel(boolean result) {
        this.result = result;
        this.code = result ? SUCCESS : ERROR;
        this.msg = result ? msg : "操作失败！";
    }

    public ResultModel(boolean result, String msg) {
        this.result = result;
        this.code = result ? SUCCESS : ERROR;
        this.msg = msg;
    }

    public ResultModel(Long count, T data) {
        this.count = count;
        this.data = data;
        this.code = SUCCESS;
    }

    public ResultModel(T data, String msg) {
        this.msg = msg;
        this.data = data;
    }

    public ResultModel(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.result = SUCCESS.equals(code);
    }

    public ResultModel(boolean result, String msg, T data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }
}
