package study.fisco.demo.service.returEnum;

public enum AssetTransferEnum {
    FROM_NULL(-1,"转移资产账户不存在"),
    TO_NULL(-2,"接收资产账户不存在"),
    NO_ENOUGH(-3,"金额不足"),
    TOO_BIG(-4,"金额溢出"),
    FAIL(-5,"其他错误");

    private int code;
    private String msg;

    AssetTransferEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
