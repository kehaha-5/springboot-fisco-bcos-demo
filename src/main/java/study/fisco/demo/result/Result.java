package study.fisco.demo.result;

public class Result<T> {

    private int code;

    private String msg;

    private T data;

    public Result() {
    }

    public static<T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static<T> Result<T> success(){
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    public static<T> Result<T> fail(String msg){
        Result<T> result = new Result<>();
        result.setMsg(msg);
        result.setCode(ResultEnum.FAIL.getCode());
        return result;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
