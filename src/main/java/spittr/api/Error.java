package spittr.api;

/**
 * @author: monkey
 * @date: 2018/6/3 17:36
 */
public class Error {

    private int code;
    private String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
