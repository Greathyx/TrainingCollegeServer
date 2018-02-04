package utils;


/**
 * 用于回传值
 * @param <T>
 */
public class ResultBundle<T> {

    private boolean successTag;  // 请求是否成功的标志
    private String message;  // 返回的信息
    private T t;  // 返回的bean对象

    public ResultBundle(boolean successTag, String message, T t) {
        this.successTag = successTag;
        this.message = message;
        this.t = t;
    }

    public boolean getSuccessTag() {
        return successTag;
    }

    public void setSuccessTag(boolean successTag) {
        this.successTag = successTag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

}
